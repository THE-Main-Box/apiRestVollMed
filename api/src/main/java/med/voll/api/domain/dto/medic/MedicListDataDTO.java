package med.voll.api.domain.dto.medic;

import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;

public record MedicListDataDTO(Long id, String nome, String email, String telefone, String crm, Speciality especialidade) {

    public MedicListDataDTO(Medic medic){
        this(medic.getId(), medic.getNome(), medic.getEmail(), medic.getTelefone(), medic.getCrm(), medic.getEspecialidade());
    }

}
