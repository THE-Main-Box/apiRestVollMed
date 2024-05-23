package med.voll.api.repository;

import aj.org.objectweb.asm.commons.Remapper;
import med.voll.api.model.medic.Medic;
import med.voll.api.model.medic.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByEspecialidade(Speciality especialidade, Pageable page);
}
