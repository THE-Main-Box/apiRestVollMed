package med.voll.api.domain.dto.schedules;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleRegisterDataDTO(
        @NotNull
        Long patientId,

        Long medicId,
        @NotNull
        LocalDateTime scheduleDateTime
) {
}
