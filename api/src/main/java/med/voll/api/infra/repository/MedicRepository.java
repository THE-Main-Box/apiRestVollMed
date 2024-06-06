package med.voll.api.infra.repository;

import med.voll.api.domain.model.medic.Medic;
import med.voll.api.domain.model.medic.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SequencedCollection;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByEspecialidadeAndAtivo(Speciality especialidade, boolean ativo, Pageable page);

    Page<Medic> findByAtivo( boolean ativo, Pageable page);

    @Query("""
            SELECT m FROM Medic m
            WHERE
            m.ativo = true,
            m.especialidade = :especialidade,
            m.id not in(
                SELECT s.medic.id FROM Schedule s
                WHERE
                s.scheduleDateTime = : dateTime
            )
            order by rand()
            limit 1
            """)
    Medic encontraMedicoLivreNaDataPorEspecialidade(Speciality especialidade, LocalDateTime dateTime);
}
