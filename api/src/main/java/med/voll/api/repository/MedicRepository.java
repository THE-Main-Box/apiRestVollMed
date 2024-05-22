package med.voll.api.repository;

import jakarta.transaction.Transactional;
import med.voll.api.model.medic.Medic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface MedicRepository extends JpaRepository<Medic, Long> {
}
