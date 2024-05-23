package med.voll.api.dto.medic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.adress.CompleteAdressRec;

public record MedicUpdateDataDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @Valid
        @NotNull
        CompleteAdressRec endereco
) {
}
