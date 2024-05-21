package med.voll.api.model.medic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.MedicRegisterDataDTO;
import med.voll.api.model.adress.CompleteAdress;

@Entity
@Table(name = "medics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medic {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Speciality especialidade;

    @Embedded
    private CompleteAdress endereco;


    public Medic(MedicRegisterDataDTO dataDTO) {
        this.nome = dataDTO.nome();
        this.email = dataDTO.email();
        this.crm = dataDTO.crm();
        this.especialidade = dataDTO.especialidade();
        this.endereco = new CompleteAdress(dataDTO.endereco());
    }
}
