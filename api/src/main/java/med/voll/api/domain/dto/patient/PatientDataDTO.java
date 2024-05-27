package med.voll.api.domain.dto.patient;

import med.voll.api.domain.model.patient.Patient;

public record PatientDataDTO(Long id, String nome, String email, String cpf) {

    public PatientDataDTO(Patient paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCPF());
    }

}
