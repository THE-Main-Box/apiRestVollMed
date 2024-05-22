package med.voll.api.model.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.MedicRegisterDataDTO;
import med.voll.api.dto.PatientRegisterDataDTO;
import med.voll.api.model.adress.CompleteAdress;
import med.voll.api.model.medic.Speciality;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String CPF;
    private String telefone;

    @Embedded
    private CompleteAdress endereco;


    public Patient(PatientRegisterDataDTO dataDTO) {
        this.nome = dataDTO.nome();
        this.email = dataDTO.email();
        this.CPF = dataDTO.cpf();
        this.endereco = new CompleteAdress(dataDTO.endereco());
        this.telefone =dataDTO.telefone();
    }

}
