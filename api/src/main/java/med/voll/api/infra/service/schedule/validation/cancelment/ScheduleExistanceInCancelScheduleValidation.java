package med.voll.api.infra.service.schedule.validation.cancelment;

import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCancelmentOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleExistanceInCancelScheduleValidation implements ValidatorCancelmentOfSchedules {
    @Autowired
    private ScheduleRepository repository;

    @Override
    public void validate(ScheduleCancelDataDTO dataDTO){
        if (!repository.existsById(dataDTO.scheduleId())) {
            throw new ScheduleIntegrityException("Não existe essa consulta na agenda");
        }
    }
}
