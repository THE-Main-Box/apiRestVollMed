package med.voll.api.dto;

import med.voll.api.model.adress.CompleteAdressRec;
import med.voll.api.model.medic.Speciality;

public record MedicRegisterDataDTO(
        String nome,
        String email,
        String crm,
        Speciality especialidade,
        CompleteAdressRec endereco
) {}