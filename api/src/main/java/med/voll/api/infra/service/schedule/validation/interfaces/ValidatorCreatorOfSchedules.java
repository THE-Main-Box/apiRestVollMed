package med.voll.api.infra.service.schedule.validation.interfaces;

import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;

public interface ValidatorCreatorOfSchedules {
    void validate(ScheduleRegisterDataDTO dataDTO);
}
