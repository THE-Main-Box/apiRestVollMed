package med.voll.api.infra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import med.voll.api.domain.model.patient.Patient;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private Patient patient;

    private Medic medic;

    private LocalDateTime nextMondayAt10;

    private Schedule schedule;

    private final String SCHEDULE_REGISTER_URL = "/schedule/register";
    private final String SCHEDULE_CANCEL_URL = "/schedule/cancel";

    @BeforeEach
    public void setTestData() {
        nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        // Cria e salva o médico apenas uma vez
        medic = new Medic("medic", "medic@voll.med", "123456", "7100004444", Speciality.CARDIOLOGIA);
        medic = medicRepository.save(medic);

        // Cria e salva o paciente apenas uma vez
        patient = new Patient("patient", "patient@gmail.com", "00011133344", "8100011122");
        patient = patientRepository.save(patient);

        schedule = new Schedule(nextMondayAt10, medic, patient);
        schedule = scheduleRepository.save(schedule);
    }

    @Test
    @DisplayName("Deveria devolver http:400 caso dados estejam sendo passados incorretamente")
    @WithMockUser
    void registerSchedule_Scene1() throws Exception {
        var invalidData = new ScheduleRegisterDataDTO(null, null, null, null);

        var response = mvc.perform(post(SCHEDULE_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver http:400 caso a data da schedule seja antes de agora ou agora")
    @WithMockUser
    void registerSchedule_Scene2() throws Exception {
        var invalidData = new ScheduleRegisterDataDTO(patient.getId(), medic.getId(), null, LocalDateTime.now());

        var response = mvc.perform(post(SCHEDULE_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver http:201 quando passado todas as informações exceto a especialidade")
    @WithMockUser
    void registerSchedule_Scene3() throws Exception {
        var validData = new ScheduleRegisterDataDTO(patient.getId(), medic.getId(), null, nextMondayAt10);

        var response = mvc.perform(post(SCHEDULE_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deveria devolver http:201 quando passado todas as informações exceto o medicId mas passando a especialidade")
    @WithMockUser
    void registerSchedule_Scene4() throws Exception {
        var validData = new ScheduleRegisterDataDTO(patient.getId(), null, Speciality.DERMATOLOGIA, nextMondayAt10);

        var response = mvc.perform(post(SCHEDULE_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deveria devolver http:500 quando passado todas as informações exceto o medicId e a especialidade")
    @WithMockUser
    void registerSchedule_Scene5() throws Exception {
        var invalidData = new ScheduleRegisterDataDTO(patient.getId(), null, null, nextMondayAt10);

        var response = mvc.perform(post(SCHEDULE_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("Deveria devolver http:200 ao cancelar um agendamento válido")
    @WithMockUser
    void cancelSchedule_Scene1() throws Exception {
        ScheduleCancelDataDTO cancelData = new ScheduleCancelDataDTO(schedule.getId(), "Cancelando agendamento");

        var response = mvc.perform(put(SCHEDULE_CANCEL_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelData)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}