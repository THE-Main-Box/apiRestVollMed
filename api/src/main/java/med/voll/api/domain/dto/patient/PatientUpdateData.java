package med.voll.api.domain.dto.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.model.adress.CompleteAdressRec;

public record PatientUpdateData(

        @NotBlank
        String nome,

        @NotBlank
        String telefone,

        @NotNull @Valid
        CompleteAdressRec endereco
) {
}
