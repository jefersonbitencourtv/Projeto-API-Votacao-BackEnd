package api.desafio.domain.repository;

import api.desafio.domain.entities.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<PautaEntity, Long> {
}
