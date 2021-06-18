package api.desafio.domain.repository;

import api.desafio.domain.entities.VotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotacaoRepository extends JpaRepository<VotacaoEntity, Long> {
    Optional<VotacaoEntity> findByIdPauta(Long Id_Pauta);
}
