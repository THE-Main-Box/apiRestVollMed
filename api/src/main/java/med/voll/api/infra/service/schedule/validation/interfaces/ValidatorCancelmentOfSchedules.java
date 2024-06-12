package med.voll.api.infra.service.schedule.validation.interfaces;

import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;

public interface ValidatorCancelmentOfSchedules {
    void validate(ScheduleCancelDataDTO dataDTO);
}
