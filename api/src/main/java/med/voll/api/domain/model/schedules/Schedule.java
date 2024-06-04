package med.voll.api.domain.model.schedules;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.patient.Patient;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private PatientRepository patientRepository;

    private Medic medic;
    private Patient patient;

    private LocalDateTime scheduleDateTime;



    public Schedule(ScheduleRegisterDataDTO dataDTO) {
        this.scheduleDateTime = dataDTO.scheduleDateTime();
        this.setMedic(dataDTO.medicId());
        this.setPatient(dataDTO.patientId());
    }

    public void setMedic(Long id){
        this.medic = medicRepository.getReferenceById(id);
    }

    public void setPatient(Long id){
        this.patient = patientRepository.getReferenceById(id);
    }

}
