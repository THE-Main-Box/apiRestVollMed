package med.voll.api.dto.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.model.adress.CompleteAdressRec;

public record PatientUpdateData(

        @NotBlank
        String nome,

        @NotBlank
        String telefone,

        @NotNull @Valid
        CompleteAdressRec endereco
) {
}
