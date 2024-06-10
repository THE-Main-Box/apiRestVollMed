package med.voll.api.domain.dto.schedules;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleCancelDataDTO(

        @NotNull
        Long scheduleId,
        @NotBlank
        String cancelMotive

) {
}
