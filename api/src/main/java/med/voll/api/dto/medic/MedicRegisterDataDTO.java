package med.voll.api.dto.medic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.model.adress.CompleteAdressRec;
import med.voll.api.model.medic.Speciality;

public record MedicRegisterDataDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotNull
        Speciality especialidade,

        @NotNull
        @Valid
        CompleteAdressRec endereco
) {
}