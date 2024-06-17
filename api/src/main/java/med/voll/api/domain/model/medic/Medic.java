package med.voll.api.domain.model.medic;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.model.adress.CompleteAdress;
import med.voll.api.domain.dto.medic.MedicRegisterDataDTO;
import med.voll.api.domain.dto.medic.MedicUpdateDataDTO;
import med.voll.api.domain.model.schedules.Schedule;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String crm;
    private String telefone;
    @Setter
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Speciality especialidade;

    @Embedded
    private CompleteAdress endereco;


    @Transient
    @OneToMany(mappedBy = "medic",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Schedule> consults = new ArrayList<>();

    public Medic(MedicRegisterDataDTO dataDTO) {
        this.nome = dataDTO.nome();
        this.email = dataDTO.email();
        this.crm = dataDTO.crm();
        this.especialidade = dataDTO.especialidade();
        this.endereco = new CompleteAdress(dataDTO.endereco());
        this.telefone = dataDTO.telefone();
        this.ativo = true;
    }

    public Medic(String nome, String email, String crm, String telefone, Speciality especialidade) {
        this.nome = nome;
        this.email = email;
        this.crm = crm;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.ativo = true;
        this.endereco = null;
    }

    public void updateData(MedicUpdateDataDTO dataDTO) {
        this.email = dataDTO.email();
        this.nome = dataDTO.nome();
        this.endereco = new CompleteAdress(dataDTO.endereco());
        this.telefone = dataDTO.telefone();
    }

    @Override
    public String toString() {
        return "Medic{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", crm='" + crm + '\'' +
                ", telefone='" + telefone + '\'' +
                ", especialidade=" + especialidade +
                ", endereco=" + endereco +
                '}';
    }
}
