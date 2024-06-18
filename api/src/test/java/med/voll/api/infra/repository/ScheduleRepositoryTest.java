package med.voll.api.infra.repository;

import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import med.voll.api.domain.model.patient.Patient;
import med.voll.api.domain.model.schedules.Schedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ScheduleRepositoryTest {
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PatientRepository patientRepository;

    private LocalDateTime nextMondayAt10;
    private Medic medic;
    private Patient patient;
    private Schedule schedule;

    private void saveSchedule() {
        this.medic = medicRepository.save(medic);
        this.patient = patientRepository.save(patient);
        this.schedule = scheduleRepository.save(schedule);
    }

    @BeforeEach
    public void setTestData() {
        this.nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        this.medic = new Medic("medic", "medic@voll.med", "123456", "7100004444", Speciality.CARDIOLOGIA);
        this.patient = new Patient("patient", "patient@gmail.com", "00011133344", "8100011122");
        this.schedule = new Schedule(this.nextMondayAt10, this.medic, this.patient);
    }

    /*//FIND BY PATIENT\\*/

    @Test
    @DisplayName("Quando você busca uma schedule pelo paciente que já está cancelada")
    void findByPatientScene1() {
        this.schedule.cancel("teste cena-1", LocalDateTime.now());
        this.saveSchedule();

        Optional<Schedule> scheduleToFind = scheduleRepository.findByPatientInDayOfYear(patient.getId(), nextMondayAt10);

        assertThat(scheduleToFind).isEmpty();
    }

    @Test
    @DisplayName("Quando você busca uma schedule pelo paciente que não está cancelada")
    void findByPatientScene2() {
        this.saveSchedule();

        Optional<Schedule> scheduleToFind = scheduleRepository.findByPatientInDayOfYear(patient.getId(), nextMondayAt10);

        assertThat(scheduleToFind.get().getPatient()).isEqualTo(patient);
    }

    /*//FIND BY MEDIC\\*/

    @Test
    @DisplayName("Quando você busca uma Schedule pelo médico que está cancelada")
    void findByMedicInDayOfYearScene1(){
        this.schedule.cancel("teste cena-2", LocalDateTime.now());
        this.saveSchedule();

        Optional<Schedule> scheduleToFind = scheduleRepository.findByPatientInDayOfYear(patient.getId(), nextMondayAt10);

        assertThat(scheduleToFind).isEmpty();
    }

    @Test
    @DisplayName("Quando você busca uma Schedule pelo médico que está cancelada")
    void findByMedicInDayOfYearScene2(){
        this.saveSchedule();

        Optional<Schedule> scheduleToFind = scheduleRepository.findByPatientInDayOfYear(patient.getId(), nextMondayAt10);

        assertThat(scheduleToFind.get().getMedic()).isEqualTo(medic);
    }
}
