package med.voll.api.infra.repository;

import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {


    Page<Medic> findByEspecialidadeAndAtivoTrue(Speciality especialidade, Pageable page);

    Page<Medic> findByAtivoTrue(Pageable page);

    @Query("""
            SELECT m FROM Medic m
            WHERE
            m.ativo = true
            And
            m.especialidade = :especialidade
            And
            m.id not in(
                SELECT s.medic.id FROM Schedule s
                WHERE
                s.scheduleDateTime = :dateTime
            )
            order by RANDOM()
            limit 1
            """)
    Optional<Medic> encontraMedicoLivreNaDataPorEspecialidade(Speciality especialidade, LocalDateTime dateTime);
}
