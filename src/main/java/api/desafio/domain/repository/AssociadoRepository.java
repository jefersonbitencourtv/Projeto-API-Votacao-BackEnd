package api.desafio.domain.repository;

import api.desafio.domain.entities.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<AssociadoEntity, Long> {

    Optional<AssociadoEntity> findByCpf(String cpf);
}

