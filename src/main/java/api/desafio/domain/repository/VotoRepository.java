package api.desafio.domain.repository;

import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<VotoEntity,Long> {
    default VotoEntity insert(VotoEntity votacao){
        return null;
    }
    //@Query(value = "SELECT * FROM Voto WHERE Id_Associado = '1'")
    Optional<VotoEntity>  findByIdAssociado(long idAssociado);

}
