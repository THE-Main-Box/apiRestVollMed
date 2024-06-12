package med.voll.api.infra.service.schedule.validation.cancelment;

import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCancelmentOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleCancelPrevioslyValidator implements ValidatorCancelmentOfSchedules {

    @Autowired
    private ScheduleRepository repository;

    @Override
    public void validate(ScheduleCancelDataDTO dataDTO) {
        Schedule schedule = repository.findById(dataDTO.scheduleId())
                .orElseThrow(() -> new IllegalStateException("Consulta não encontrada"));

        if (schedule.isCanceled()) {
            throw new ScheduleIntegrityException("Consulta cacelada préviamente");
        }
    }
}
