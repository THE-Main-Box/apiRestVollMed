package med.voll.api.infra.service.schedule.validation.creation;

import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCreatorOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduleDateTimeCreationValidation implements ValidatorCreatorOfSchedules {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final int OPENING_HOUR = 7;
    private final int CLOSING_HOUR = 19;
    private final int SCHEDULE_DURATION_IN_HOURS = 1;

    public void validate(ScheduleRegisterDataDTO dataDTO) {

        boolean isSunday = dataDTO.scheduleDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isBeforeOpening = dataDTO.scheduleDateTime().getHour() < OPENING_HOUR;
        boolean isAfterClosing = dataDTO.scheduleDateTime().getHour() > CLOSING_HOUR - SCHEDULE_DURATION_IN_HOURS;

        if (Duration.between(LocalDateTime.now(), dataDTO.scheduleDateTime()).toMinutes() < 30) {
            throw new ScheduleIntegrityException("A consulta deve ser marcada com pelo menos 30 minutos de antecendencia");

        } else if (isSunday || isBeforeOpening || isAfterClosing) {
            throw new ScheduleIntegrityException("A consulta deve ser realizada em um dia onde a clinica esteja funcionando |de segunda à sabado| das 7:00 às 18:00");

        }
    }

}
