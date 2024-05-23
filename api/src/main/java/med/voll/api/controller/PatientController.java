package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.PatientDataDTO;
import med.voll.api.dto.PatientRegisterDataDTO;
import med.voll.api.model.patient.Patient;
import med.voll.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping("/register")
    @Transactional
    public String registerPatient(@RequestBody @Valid PatientRegisterDataDTO dataDTO){
        try {
            repository.save(new Patient(dataDTO));
            return "Cadastro concluído com sucesso!";
        }catch (Exception e){
            return "Houve um erro no seu cadastro tente novamente: "+ e;
        }
    }

    @GetMapping("/list")
    public Page<PatientDataDTO> listPatient(@PageableDefault(sort = "nome") Pageable pageable){
        return repository.findAll(pageable).map(PatientDataDTO::new);
    }

}
