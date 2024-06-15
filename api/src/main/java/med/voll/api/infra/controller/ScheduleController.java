package med.voll.api.infra.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.dto.schedules.SchedulesDetailedData;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/schedule")
@SecurityRequirement(name = "bearer-key")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping("/register")
    public ResponseEntity<Object> registerSchedule(@RequestBody @Valid ScheduleRegisterDataDTO dataDTO, UriComponentsBuilder builder) {

        Schedule schedule = service.validateSchedule(dataDTO);

        URI uri = builder.path("/schedule/{id}").buildAndExpand(schedule.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/cancel")
    public ResponseEntity<Object> cancelSchedule(@RequestBody @Valid ScheduleCancelDataDTO dataDTO){
        service.validateScheduleCancelment(dataDTO);

        return ResponseEntity.ok().body("Consulta cancelada com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showSchedule(@PathVariable Long id) {
        SchedulesDetailedData schedule = service.detailDataFrom(id);
        return ResponseEntity.ok().body(schedule);
    }

}
