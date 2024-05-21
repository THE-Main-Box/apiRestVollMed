package med.voll.api.controller;

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
    public void register(@RequestBody MedicRegisterDataDTO dataDTO){

        repository.save(new Medic(dataDTO));

    }

}
