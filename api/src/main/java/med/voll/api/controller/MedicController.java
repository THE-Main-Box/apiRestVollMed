package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.MedicRegisterDataDTO;
import med.voll.api.model.adress.CompleteAdress;
import med.voll.api.model.medic.Medic;
import med.voll.api.model.medic.Speciality;
import med.voll.api.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicRepository repository;

    @PostMapping("/register")
    public String register(@RequestBody @Valid MedicRegisterDataDTO dataDTO){
        try {
            repository.save(new Medic(dataDTO));
            return "Cadastro concluído com sucesso!";
        }catch (Exception e){
         return "Houve um erro no seu cadastro tente novamente: "+ e;
        }
    }

}
