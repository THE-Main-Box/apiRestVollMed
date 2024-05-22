package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.dto.PatientRegisterDataDTO;
import med.voll.api.model.patient.Patient;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping("/register")
    public String registerPatient(@RequestBody @Valid PatientRegisterDataDTO dataDTO){
        try {
            repository.save(new Patient(dataDTO));
            return "Cadastro concluído com sucesso!";
        }catch (Exception e){
            return "Houve um erro no seu cadastro tente novamente: "+ e;
        }
    }

}
