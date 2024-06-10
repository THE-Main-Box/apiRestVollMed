package med.voll.api.infra.repository;

import med.voll.api.domain.model.schedules.ScheduleCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleCancelRepository extends JpaRepository<ScheduleCancel, Long> {
}
