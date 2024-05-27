package med.voll.api.domain.dto.patient;

import med.voll.api.domain.model.adress.CompleteAdressRec;
import med.voll.api.domain.model.patient.Patient;

public record PatientDetailedDataDTO(Long id, String nome, String email, String cpf, String telefone, CompleteAdressRec endereco) {

    public PatientDetailedDataDTO(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCPF(), patient.getTelefone(), new CompleteAdressRec(patient.getEndereco()));
    }

}
