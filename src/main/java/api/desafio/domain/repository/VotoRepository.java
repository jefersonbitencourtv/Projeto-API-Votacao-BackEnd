package api.desafio.domain.repository;

import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<VotoEntity,Long> {
    default VotoEntity insert(VotoEntity votacao){
        return null;
    }

    //@Query("SELECT v FROM votar v WHERE v.associadoEntity = :idAssociado AND v.votacaoEntity = :idVotacao")
    //Optional<VotoEntity> findByIdAssociadoAndIdVotacao(@Param("idAssociado") AssociadoEntity associado, @Param("idVotacao") VotacaoEntity votacao);
    Optional<VotoEntity> findByAssociadoEntityAndVotacaoEntity(@Param("idAssociado") AssociadoEntity associado, @Param("idVotacao") VotacaoEntity votacao);
}
