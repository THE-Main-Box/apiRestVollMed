package med.voll.api.infra.service.schedule.validation.cancelment;

import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCancelmentOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleCancelmentDateValidator implements ValidatorCancelmentOfSchedules {
    @Autowired
    private ScheduleRepository repository;

    @Override
    public void validate(ScheduleCancelDataDTO dataDTO) {
        Schedule schedule = repository.getReferenceById(dataDTO.scheduleId());
        if(LocalDateTime.now().plusHours(24).getDayOfYear() > schedule.getScheduleDateTime().getDayOfYear()){
            throw new ScheduleIntegrityException("O cancelamento da consulta deve ser feito com pelo menos 24 horas de antecedencia");
        }
    }
}
