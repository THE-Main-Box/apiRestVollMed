package med.voll.api.domain.dto.schedules;

import med.voll.api.domain.dto.medic.MedicDetailedDataDTO;
import med.voll.api.domain.dto.patient.PatientDetailedDataDTO;
import med.voll.api.domain.model.schedules.Schedule;

import java.time.LocalDateTime;

public record SchedulesDetailedData(
        Long id,
        MedicDetailedDataDTO medico,
        PatientDetailedDataDTO paciente,
        LocalDateTime data
) {
    public SchedulesDetailedData(Schedule schedule) {
        this(
                schedule.getId(),
                new MedicDetailedDataDTO(schedule.getMedic()),
                new PatientDetailedDataDTO(schedule.getPatient()),
                schedule.getScheduleDateTime()
        );
    }
}
