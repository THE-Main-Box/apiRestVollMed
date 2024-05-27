package med.voll.api.infra.repository;

import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByEspecialidadeAndAtivo(Speciality especialidade, boolean ativo, Pageable page);

    Page<Medic> findByAtivo( boolean ativo, Pageable page);
}
