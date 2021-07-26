package api.desafio.domain.repository;

import api.desafio.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<ResultadoEntity, Long> {
    /*@Query("SELECT r FROM Resultado r WHERE r.votacaoEntity = :idVotacao")
    Optional<ResultadoEntity> findByIdVotacao(@Param("idVotacao") VotacaoEntity votacao);

    @Query("SELECT r FROM Resultado r WHERE r.pautaEntity = :idPauta")
    Optional<ResultadoEntity> findByIdPauta(@Param("idPauta") PautaEntity pauta);*/
    Optional<ResultadoEntity> findByVotacaoEntity(@Param("idVotacao") VotacaoEntity votacao);
    Optional<ResultadoEntity> findByPautaEntity(@Param("idPauta") PautaEntity pauta);

}
