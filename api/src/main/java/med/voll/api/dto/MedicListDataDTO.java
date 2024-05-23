package med.voll.api.dto;

import med.voll.api.model.medic.Medic;
import med.voll.api.model.medic.Speciality;

public record MedicListDataDTO(String nome, String email, String telefone, String crm, Speciality especialidade) {

    public MedicListDataDTO(Medic medic){
        this(medic.getNome(), medic.getEmail(), medic.getTelefone(), medic.getCrm(), medic.getEspecialidade());
    }

}
