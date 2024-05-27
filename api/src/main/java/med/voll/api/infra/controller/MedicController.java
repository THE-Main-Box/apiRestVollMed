package med.voll.api.infra.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.medic.MedicDetailedDataDTO;
import med.voll.api.domain.dto.medic.MedicListDataDTO;
import med.voll.api.domain.dto.medic.MedicRegisterDataDTO;
import med.voll.api.domain.dto.medic.MedicUpdateDataDTO;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import med.voll.api.infra.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medic")
public class MedicController {

    @Autowired
    private MedicRepository repository;

    /*    valida os dados da requestBody e verifica se estão validos se estiverem salva um novo medico atravéz da record
     *    MedicRegisterDataDTO*/

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> register(@RequestBody @Valid MedicRegisterDataDTO dataDTO, UriComponentsBuilder uriBuilder) {
        Medic medic = new Medic(dataDTO);

        repository.save(medic);

        URI uri = uriBuilder.path("/medic/{id}").buildAndExpand(medic.getId()).toUri();

        return ResponseEntity.created(uri).body(detail(medic));
    }

    //      mapeia o banco para retornar uma lista de médicos e depois converte para uma DTO
    @GetMapping("/list")
    public ResponseEntity<Page<MedicListDataDTO>> listMedic(@PageableDefault(size = 10, sort = "nome") Pageable page) {

        return ResponseEntity.ok(repository.findByAtivo(true, page).map(MedicListDataDTO::new));

    }

    /*    mapeia o banco para retornar uma lista de médicos e depois converte para uma DTO porém
     *     filtrando pelos campos de especialidade e retorna apenas os medicos
     *     com a especialidade especificada */
    @GetMapping("/list/{especialidade}")
    public ResponseEntity<Page<MedicListDataDTO>> listMedicBySpeciality(@PathVariable Speciality especialidade, @PageableDefault(size = 10, sort = "nome") Pageable page) {

        return ResponseEntity.ok((repository.findByEspecialidadeAndAtivo(especialidade, true, page).map(MedicListDataDTO::new)));

    }

    /* mostra os dados completos de um médico a partir de uma id passada pelo url*/
    @GetMapping("/{id}")
    public ResponseEntity<MedicDetailedDataDTO> showMedicDetailedData(@PathVariable Long id){
        Medic medic = repository.getReferenceById(id);
        return ResponseEntity.ok(detail(medic));
    }

    /*atualiza os capos atualizaveis da entidade medic
     * e por estar com a anotação transictional ela atualiza o banco automaticamente */
    @PutMapping("/{id}/update")
    @Transactional
    public ResponseEntity<Object> updateMedic(@PathVariable Long id, @RequestBody @Valid MedicUpdateDataDTO dataDTO) {
        Medic medic = repository.getReferenceById(id);
        medic.updateData(dataDTO);

        return ResponseEntity.ok(detail(medic));
    }

    @DeleteMapping("/{id}/delete")
    @Transactional
    public ResponseEntity<Object> deleteMedic(@PathVariable Long id) {
        Medic medic = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medic not found"));
        medic.setAtivo(false);
        return ResponseEntity.noContent().build();
    }


    private MedicDetailedDataDTO detail(Medic medic){
        return new MedicDetailedDataDTO(medic);
    }

}
