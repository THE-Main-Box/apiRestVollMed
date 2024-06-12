package med.voll.api.infra.service.schedule.validation.creation;

import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.patient.Patient;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCreatorOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleMedicPatientCreationValidation implements ValidatorCreatorOfSchedules {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRepository medicRepository;

    public void validate(ScheduleRegisterDataDTO dataDTO) {
        Medic medic = null;
        if(dataDTO.medicId() != null) {
            medic = medicRepository.getReferenceById(dataDTO.medicId());
        }
        Patient patient = patientRepository.getReferenceById(dataDTO.patientId());
        LocalDateTime dayOfYear = dataDTO.scheduleDateTime();

        if (scheduleRepository.findByPatient(patient.getId(), dayOfYear).isPresent()) {
            throw new ScheduleIntegrityException("Um paciente só pode ter uma consulta agendada por dia");
        } else if (!patientRepository.existsById(dataDTO.patientId())) {
            throw new ScheduleIntegrityException("Paciente não existe");
        } else if (dataDTO.medicId()!= null && !medicRepository.existsById(dataDTO.medicId())) {
            throw new ScheduleIntegrityException("Medico não existe");
        } else if (medic != null &&!medic.isAtivo()) {
            throw new ScheduleIntegrityException("A id do médico deve pertencer a um médico ativo na plataforma");
        }
    }
}
