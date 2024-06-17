package med.voll.api.domain.model.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.patient.PatientRegisterDataDTO;
import med.voll.api.domain.dto.patient.PatientUpdateData;
import med.voll.api.domain.model.adress.CompleteAdress;
import med.voll.api.domain.model.schedules.Schedule;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Schedule> consults = new ArrayList<>();

    public Patient(PatientRegisterDataDTO dataDTO) {
        this.nome = dataDTO.nome();
        this.email = dataDTO.email();
        this.CPF = dataDTO.cpf();
        this.endereco = new CompleteAdress(dataDTO.endereco());
        this.telefone =dataDTO.telefone();
    }

    public Patient(String nome, String email, String CPF, String telefone) {
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.telefone = telefone;
    }

    public void updateData(PatientUpdateData dataDTO){
        this.nome = dataDTO.nome();
        this.endereco = new CompleteAdress(dataDTO.endereco());
        this.telefone = dataDTO.telefone();
    }

}
