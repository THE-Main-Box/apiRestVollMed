package med.voll.api.infra.repository;

import med.voll.api.domain.model.schedules.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

//    verifica se uma consulta existe em um ddia especifico do ano para um determinado paciente
    @Query("""
            SELECT s FROM Schedule s
            WHERE
            s.isCanceled = false
            AND
            s.patient.id = :patientId
            AND
            EXTRACT(YEAR FROM s.scheduleDateTime) = EXTRACT(YEAR FROM CAST(:dateTime AS TIMESTAMP))
            AND
            EXTRACT(MONTH FROM s.scheduleDateTime) = EXTRACT(MONTH FROM CAST(:dateTime AS TIMESTAMP))
            AND
            EXTRACT(DAY FROM s.scheduleDateTime) = EXTRACT(DAY FROM CAST(:dateTime AS TIMESTAMP))
            """)
    Optional<Schedule> findByPatientInDayOfYear(Long patientId, LocalDateTime dateTime);

//    verifica se uma consulta existe em um dia especifico do ano para um determinado medico
    @Query("""
            SELECT s FROM Schedule s
            WHERE
            s.isCanceled = false
            AND
            s.medic.id = :medicId
            AND
            EXTRACT(YEAR FROM s.scheduleDateTime) = EXTRACT(YEAR FROM CAST(:dateTime AS TIMESTAMP))
            AND
            EXTRACT(MONTH FROM s.scheduleDateTime) = EXTRACT(MONTH FROM CAST(:dateTime AS TIMESTAMP))
            AND
            EXTRACT(DAY FROM s.scheduleDateTime) = EXTRACT(DAY FROM CAST(:dateTime AS TIMESTAMP))
            """)
    Optional<Schedule> findByMedicInDayOfYear(Long medicId, LocalDateTime dateTime);
}
