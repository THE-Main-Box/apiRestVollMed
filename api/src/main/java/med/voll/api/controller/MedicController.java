package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.MedicListDataDTO;
import med.voll.api.dto.MedicRegisterDataDTO;
import med.voll.api.model.medic.Medic;
import med.voll.api.model.medic.Speciality;
import med.voll.api.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicRepository repository;

    /*    valida os dados da requestBody e verifica se estão validos se estiverem salva um novo medico atravéz da record
     *    MedicRegisterDataDTO*/
    @PostMapping("/register")
    @Transactional
    public String register(@RequestBody @Valid MedicRegisterDataDTO dataDTO) {
        try {
            repository.save(new Medic(dataDTO));
            return "Cadastro concluído com sucesso!";
        } catch (Exception e) {
            return "Houve um erro no seu cadastro tente novamente: " + e;
        }
    }

    //      mapeia o banco para retornar uma lista de médicos e depois converte para uma DTO
    @GetMapping("/list")
    public Page<MedicListDataDTO> listMedic(@PageableDefault(size = 10, sort = "nome") Pageable page) {
        return repository.findAll(page).map(MedicListDataDTO::new);
    }

/*    faz a mesma coisa que o método acima porém,
*     filtra pelos campos de especialidade e retorna apenas os medicos
*     com a especialidade especificada */
    @GetMapping("/list/{especialidade}")
    public Page<MedicListDataDTO> listMedicBySpeciality(@PathVariable Speciality especialidade, @PageableDefault(size = 10, sort = "nome") Pageable page) {
        return repository.findByEspecialidade(especialidade, page).map(MedicListDataDTO::new);
    }

}
