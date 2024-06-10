package med.voll.api.infra.service.schedule;

import jakarta.transaction.Transactional;
import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.dto.schedules.SchedulesDetailedData;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.domain.model.schedules.ScheduleCancel;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleCancelRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private ScheduleCancelRepository scheduleCancelRepository;

    //    buscando banco de dados uma referencia por id de uma Schedule e retorna seus dados detalhados
    public SchedulesDetailedData detailDataFrom(Long id) {
        Schedule schedule = repository.getReferenceById(id);
        return new SchedulesDetailedData(schedule);
    }

    @Transactional
    public Schedule validateSchedule(ScheduleRegisterDataDTO dataDTO) {
        if (!patientRepository.existsById(dataDTO.patientId())) {
            throw new ScheduleIntegrityException("id do paciente não passado ou paciente não existe");
        }
        if (dataDTO.medicId() != null && !medicRepository.existsById(dataDTO.medicId())) {
            throw new ScheduleIntegrityException("id do medico não passado ou medico não existe");
        }

        Schedule schedule = newScheduleObject(dataDTO);
        return repository.save(schedule);
    }

    //    instancia e inicializa um objeto do tipo schedule
    public Schedule newScheduleObject(ScheduleRegisterDataDTO dataDTO) {
        return new Schedule(
                dataDTO.scheduleDateTime(),
                this.selectMedic(dataDTO),
                patientRepository.getReferenceById(dataDTO.patientId())
        );
    }

    private Medic selectMedic(ScheduleRegisterDataDTO dataDTO) {
        if (dataDTO.medicId() != null) {
            return medicRepository.getReferenceById(dataDTO.medicId());
        } else if (dataDTO.especialidade() != null) {
            return this.selectAleatoryMedicFromSpeciality(dataDTO);
        } else {
            throw new ScheduleIntegrityException("Dados insuficientes");
        }
    }

    public void validateScheduleCancelment(ScheduleCancelDataDTO dataDTO) {
        if (!repository.existsById(dataDTO.scheduleId())) {
            throw new ScheduleIntegrityException("Não existe essa consulta na agenda");
        } else if (dataDTO.cancelMotive().isEmpty()) {
            throw new ScheduleIntegrityException("Motivo do cancelamento é obrigatório");
        } else if (this.validateCancelmentDate(dataDTO)) {
            cancelSchedule(dataDTO);
        }
    }

    private boolean validateCancelmentDate(ScheduleCancelDataDTO dataDTO) {
        Schedule schedule = repository.getReferenceById(dataDTO.scheduleId());

        return schedule.getScheduleDateTime().getDayOfYear() > LocalDateTime.now().getDayOfYear();
    }

    @Transactional
    private void cancelSchedule(ScheduleCancelDataDTO dataDTO) {
        Schedule schedule = repository.findById(dataDTO.scheduleId())
                .orElseThrow(() -> new IllegalStateException("Consulta não encontrada"));

        if(schedule.isCanceled()){
            throw new RuntimeException("Consulta cacelada préviamente");
        }

        LocalDateTime dateTimeNow = LocalDateTime.now();

        schedule.cancel(dataDTO.cancelMotive(), dateTimeNow);

        scheduleCancelRepository.save(schedule.getScheduleCancel());
        repository.save(schedule);

    }

    private Medic selectAleatoryMedicFromSpeciality(ScheduleRegisterDataDTO dataDTO) {
        Optional<Medic> medico = medicRepository.encontraMedicoLivreNaDataPorEspecialidade(dataDTO.especialidade(), dataDTO.scheduleDateTime());

        return medico.orElse(null);

    }
}
