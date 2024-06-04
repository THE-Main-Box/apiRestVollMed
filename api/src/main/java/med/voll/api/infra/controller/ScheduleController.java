package med.voll.api.infra.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.dto.schedules.SchedulesDetailedData;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> registerSchedule(@RequestBody @Valid ScheduleRegisterDataDTO dataDTO, UriComponentsBuilder builder){
        System.out.println(dataDTO);

        Schedule schedule = new Schedule(dataDTO);

        URI uri = builder.path("/schedule/{id}").buildAndExpand(schedule.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> showSchedule(@PathVariable Long id){
        Schedule schedule = scheduleRepository.getReferenceById(id);
        return ResponseEntity.ok().body(detailedData(schedule));
    }

    private SchedulesDetailedData detailedData(Schedule schedule){
        return new SchedulesDetailedData(schedule);
    }
}
