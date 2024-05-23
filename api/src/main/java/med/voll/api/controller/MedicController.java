package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.dto.medic.MedicListDataDTO;
import med.voll.api.dto.medic.MedicRegisterDataDTO;
import med.voll.api.dto.medic.MedicUpdateDataDTO;
import med.voll.api.model.adress.CompleteAdress;
import med.voll.api.model.medic.Medic;
import med.voll.api.model.medic.Speciality;
import med.voll.api.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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


/*atualiza os capos atualizaveis da entidade medic
* e por estar com a anotação transictional ela atualiza o banco automaticamente */
    @PutMapping("/{id}/update")
    @Transactional
    public void updateMedic(@PathVariable Long id, @RequestBody @Valid MedicUpdateDataDTO dataDTO) {
        Medic medic = repository.getReferenceById(id);
        medic.updateData(dataDTO);

    }

    @DeleteMapping("/{id}/delete")
    @Transactional
    public void deleteMedicById(@PathVariable Long id){
        Medic medic = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medic not found"));
        repository.delete(medic);
    }

}
