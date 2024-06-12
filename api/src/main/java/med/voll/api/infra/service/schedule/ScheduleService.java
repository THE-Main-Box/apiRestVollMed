package med.voll.api.infra.service.schedule;

import jakarta.transaction.Transactional;
import med.voll.api.domain.dto.schedules.ScheduleCancelDataDTO;
import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.dto.schedules.SchedulesDetailedData;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleCancelRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCancelmentOfSchedules;
import med.voll.api.infra.service.schedule.validation.interfaces.ValidatorCreatorOfSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private List<ValidatorCreatorOfSchedules> creationValidators;

    @Autowired
    private List<ValidatorCancelmentOfSchedules> cancelmentValidators;

    //    buscando banco de dados uma referencia por id de uma Schedule e retorna seus dados detalhados
    public SchedulesDetailedData detailDataFrom(Long id) {
        Schedule schedule = repository.getReferenceById(id);
        return new SchedulesDetailedData(schedule);
    }

    @Transactional
    public Schedule validateSchedule(ScheduleRegisterDataDTO dataDTO) {
        creationValidators.forEach(v -> v.validate(dataDTO));

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


    /* caso o id do medico esteja presente adiciona o próprio na schedule
     * caso não possua o id do medico verifica se possui a especialidade se não possuir nenhum dos 2 lança uma exception
     * caso não possua um id de médico mas possuir a especialidade  chama um metodo para selecionar um medico
     * aleatorio disponivel em uma data a partir de sua especialidade*/
    private Medic selectMedic(ScheduleRegisterDataDTO dataDTO) {
        if (dataDTO.medicId() != null) {
            return medicRepository.getReferenceById(dataDTO.medicId());
        } else if (dataDTO.especialidade() != null) {
            return this.selectAleatoryMedicFromSpeciality(dataDTO);
        } else {
            throw new ScheduleIntegrityException("Dados insuficientes");
        }
    }

    /*lança uma exception para cada parametro que não atingiu as especificações
     *verifica se a consulta já existe e verifica
     *também se o motivo do cancelamento está em branco
     *e também verifica se o cancelamento(agora) da consulta não está sendo feito em cima da hora*/
    public void validateScheduleCancelment(ScheduleCancelDataDTO dataDTO) {
        cancelmentValidators.forEach(cv -> cv.validate(dataDTO));
        cancelSchedule(dataDTO);
    }

    @Transactional
    private void cancelSchedule(ScheduleCancelDataDTO dataDTO) {
        Schedule schedule = repository.findById(dataDTO.scheduleId())
                .orElseThrow(() -> new IllegalStateException("Consulta não encontrada"));

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
