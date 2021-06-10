package api.desafio.domain.repository;

import api.desafio.domain.entities.VotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<VotoEntity,Long> {
}
