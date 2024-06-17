package med.voll.api.infra.service.schedule.validation.creation;

import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCreatorOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ScheduleCreationMedicAvaliableValidator implements ValidatorCreatorOfSchedules {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void validate(ScheduleRegisterDataDTO dataDTO) {

        if (scheduleRepository.findByMedicInDayOfYear(dataDTO.medicId(), dataDTO.scheduleDateTime()).isPresent()) {
            throw new ScheduleIntegrityException("Este médico já possui uma consulta agendada para esta DATA: "
                    + dataDTO.scheduleDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "às: " +
                    dataDTO.scheduleDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            );
        }

    }
}
