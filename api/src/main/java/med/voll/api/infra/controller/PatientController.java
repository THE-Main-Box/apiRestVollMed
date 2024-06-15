package med.voll.api.infra.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.patient.PatientResponseDataDTO;
import med.voll.api.domain.dto.patient.PatientDetailedDataDTO;
import med.voll.api.domain.dto.patient.PatientRegisterDataDTO;
import med.voll.api.domain.dto.patient.PatientUpdateData;
import med.voll.api.domain.model.patient.Patient;
import med.voll.api.infra.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patient")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    private PatientDetailedDataDTO detail(Patient patient){
        return new PatientDetailedDataDTO(patient);
    }

    @Autowired
    private PatientRepository repository;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> registerPatient(@RequestBody @Valid PatientRegisterDataDTO dataDTO, UriComponentsBuilder uriBuilder) {
        Patient patient = new Patient(dataDTO);

        repository.save(patient);

        URI uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(detail(patient));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PatientResponseDataDTO>> listPatient(@PageableDefault(sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(PatientResponseDataDTO::new));
    }

    @PutMapping("/{id}/update")
    @Transactional
    public ResponseEntity<Object> updatePatient(@PathVariable Long id, @RequestBody @Valid PatientUpdateData updateData) {
        Patient patient = repository.getReferenceById(id);
        patient.updateData(updateData);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showDetailedPatient(@PathVariable Long id) {
        Patient patient = repository.getReferenceById(id);

        return ResponseEntity.ok(detail(patient));

    }

    @DeleteMapping("/{id}/delete")
    @Transactional
    public ResponseEntity<Object> deletePatient(@PathVariable Long id) {
        Patient patientFound = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medic not found"));
        repository.delete(patientFound);

        return ResponseEntity.noContent().build();

    }

}
