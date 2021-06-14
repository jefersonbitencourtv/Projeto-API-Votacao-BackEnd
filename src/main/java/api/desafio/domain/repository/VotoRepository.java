package api.desafio.domain.repository;

import api.desafio.domain.entities.VotarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<VotarEntity,Long> {
    default VotarEntity insert(VotarEntity votacao){
        return null;
    }

    //@Query("SELECT v FROM Voto v WHERE v.id_votacao =:voto")
    //Optional<VotarEntity>  findByIdAssociado(long idAssociado);

    //@Query("SELECT v FROM Voto v WHERE v.id_associado =:idAssociado AND v.id_votacao =:idVotacao")
    @Query("SELECT v FROM votar v WHERE v.idAssociado = :idAssociado AND v.idVotacao = :idVotacao")
    Optional<VotarEntity> findByIdAssociadoAndIdVotacao(@Param("idAssociado") long associado, @Param("idVotacao") long votacao);

}
