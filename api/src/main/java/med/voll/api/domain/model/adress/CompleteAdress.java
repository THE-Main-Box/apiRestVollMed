package med.voll.api.domain.model.adress;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteAdress {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String complemento;
    private String numero;

    public CompleteAdress(CompleteAdressRec dataDTO) {
        this.logradouro = dataDTO.logradouro();
        this.bairro = dataDTO.bairro();
        this.cep = dataDTO.cep();
        this.cidade = dataDTO.cidade();
        this.uf = dataDTO.uf();
        this.complemento = dataDTO.complemento();
        this.numero = dataDTO.numero();
    }
}
