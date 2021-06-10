package api.desafio.domain.repository;

import api.desafio.domain.entities.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PautaRepository extends JpaRepository<PautaEntity, Long> {

    default PautaEntity insert(PautaEntity pauta) {
        return null;
    }
}
