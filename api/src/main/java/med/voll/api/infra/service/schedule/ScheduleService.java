package med.voll.api.infra.service.schedule;

import med.voll.api.domain.dto.schedules.ScheduleRegisterDataDTO;
import med.voll.api.domain.dto.schedules.SchedulesDetailedData;
import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.schedules.Schedule;
import med.voll.api.infra.exception.ScheduleIntegrityException;
import med.voll.api.infra.repository.MedicRepository;
import med.voll.api.infra.repository.PatientRepository;
import med.voll.api.infra.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRepository medicRepository;

    //    buscando banco de dados uma referencia por id de uma Schedule e retorna seus dados detalhados
    public SchedulesDetailedData detailDataFrom(Long id) {
        Schedule schedule = repository.getReferenceById(id);
        return new SchedulesDetailedData(schedule);
    }

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

    private Medic selectAleatoryMedicFromSpeciality(ScheduleRegisterDataDTO dataDTO) {
        return medicRepository.encontraMedicoLivreNaDataPorEspecialidade(dataDTO.especialidade(), dataDTO.scheduleDateTime());
    }

}
