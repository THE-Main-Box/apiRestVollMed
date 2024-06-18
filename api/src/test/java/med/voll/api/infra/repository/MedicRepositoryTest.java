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
class MedicRepositoryTest {

    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    private LocalDateTime nextMondayAt10;
    private Medic medic;
    private Patient patient;
    private Schedule schedule;

    private void saveMedic() {
        medicRepository.save(medic);
    }
    private void saveSchedule() {
        scheduleRepository.save(schedule);
    }
    public MedicRepositoryTest() {
        this.nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        this.medic = new Medic("medic", "medic@voll.med", "123456", "7100004444", Speciality.CARDIOLOGIA);
        this.patient = new Patient("patient", "patient@gmail.com", "00011133344", "8100011122");
        this.schedule = new Schedule(this.nextMondayAt10, this.medic, this.patient);
    }

    @Test
    @DisplayName("Devolve null quando há um médico cadastrado, porém não pode atendê-lo em tal horário")
    void encontraMedicoLivreNaDataPorEspecialidade_Scene1() {
        saveMedic();
        saveSchedule();
        Optional<Medic> freeMedic = medicRepository.encontraMedicoLivreNaDataPorEspecialidade(Speciality.CARDIOLOGIA, nextMondayAt10);
        assertThat(freeMedic).isEmpty();
    }

    @Test
    @DisplayName("Devolve um médico que está disponivel no seu horário")
    void encontraMedicoLivreNaDataPorEspecialidade_Scene2() {
        saveMedic();
        saveSchedule();
        Optional<Medic> freeMedic = medicRepository.encontraMedicoLivreNaDataPorEspecialidade(Speciality.CARDIOLOGIA, nextMondayAt10.plusDays(1));
        assertThat(freeMedic.get()).isEqualTo(medic);
    }

}