package med.voll.api.domain.model.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CompleteAdressRec(
        @NotBlank
        String logradouro,

        @NotBlank
        String bairro,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,

        @NotBlank
        String cidade,

        @NotBlank
        String uf,

        String complemento,
        String numero
){
    public CompleteAdressRec(CompleteAdress adress) {
        this(adress.getLogradouro(), adress.getBairro(), adress.getCep(), adress.getCidade(), adress.getUf(), adress.getComplemento(), adress.getNumero());
    }
}