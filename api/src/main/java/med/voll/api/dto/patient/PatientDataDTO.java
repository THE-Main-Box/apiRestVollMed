package med.voll.api.dto.patient;

import med.voll.api.model.patient.Patient;

public record PatientDataDTO(Long id, String nome, String email, String cpf) {

    public PatientDataDTO(Patient paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCPF());
    }

}
