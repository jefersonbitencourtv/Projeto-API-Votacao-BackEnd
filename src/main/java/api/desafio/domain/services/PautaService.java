package api.desafio.domain.services;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PautaService {
    @Autowired
    private PautaRepository rep;

    public Iterable<PautaEntity> getPauta() {
        return rep.findAll();
    }

    public Optional<PautaEntity> getPautaById(Long id) {
        return rep.findById(id);
    }

    public PautaEntity save(PautaEntity pauta) {
        return rep.save(pauta);
    }
}
