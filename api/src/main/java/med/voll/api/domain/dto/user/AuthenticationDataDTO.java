package med.voll.api.domain.dto.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDataDTO(@NotBlank String login, @NotBlank String password) {
}
