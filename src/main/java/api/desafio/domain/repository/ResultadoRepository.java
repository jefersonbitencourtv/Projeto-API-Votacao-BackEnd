package api.desafio.domain.repository;

import api.desafio.domain.entities.ResultadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<ResultadoEntity, Long> {
    Optional<ResultadoEntity> findByIdVotacao(long id_votacao);

    Optional<ResultadoEntity> findByIdPauta(long id_pauta);
}
