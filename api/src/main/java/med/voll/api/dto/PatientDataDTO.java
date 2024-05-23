package med.voll.api.dto;

import med.voll.api.model.patient.Patient;

public record PatientDataDTO(String nome, String email, String cpf) {

    public PatientDataDTO(Patient paciente){
        this(paciente.getNome(), paciente.getEmail(), paciente.getCPF());
    }

}
