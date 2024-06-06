package med.voll.api.domain.dto.schedules;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.medic.Speciality;

import java.time.LocalDateTime;

public record ScheduleRegisterDataDTO(
        @NotNull
        Long patientId,

        Long medicId,
        Speciality especialidade,
        @NotNull @Future
        LocalDateTime scheduleDateTime
) {
}
