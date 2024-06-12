package med.voll.api.infra.service.schedule.validation.cancelment;

import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCancelmentOfSchedules;
import org.springframework.stereotype.Component;

@Component
public class ScheduleCancelMotiveValidator implements ValidatorCancelmentOfSchedules {

    @Override
    public void validate(ScheduleCancelDataDTO dataDTO) {
        if (dataDTO.cancelMotive().isEmpty()) {
            throw new ScheduleIntegrityException("Motivo do cancelamento é obrigatório");
        }
    }
}
