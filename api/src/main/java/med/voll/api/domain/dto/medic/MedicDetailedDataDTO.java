package med.voll.api.domain.dto.medic;

import med.voll.api.domain.model.adress.CompleteAdressRec;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;

public record MedicDetailedDataDTO(Long id, String nome, String email, String crm, Speciality especialidade,
                                   CompleteAdressRec endereco, String telefone) {

    public MedicDetailedDataDTO(Medic medic) {
        this(medic.getId(), medic.getNome(), medic.getEmail(), medic.getCrm(), medic.getEspecialidade(), new CompleteAdressRec(medic.getEndereco()), medic.getTelefone());
    }

}