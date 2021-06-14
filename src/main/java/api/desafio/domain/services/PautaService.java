package api.desafio.domain.services;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PautaService {
    @Autowired
    private PautaRepository rep;

    public List<PautaDTO> getPauta() {
        /*List<PautaEntity> pauta = rep.findAll();
        List<PautaDTO> pdto = new ArrayList<>();
        for(PautaEntity p : pauta ){
            pdto.add(new PautaDTO(p));
        }
        return pdto;*/
        return rep.findAll().stream().map(p -> new PautaDTO(p)).collect(Collectors.toList());
    }

    public PautaDTO getPautaById(Long id) {
        /*Optional<PautaEntity> pauta = rep.findById(id);
        if(pauta.isPresent()){
            return Optional.of(new PautaDTO(pauta.get()));
        }else{
            return null;
        }*/
        return rep.findById(id).map(p -> new PautaDTO(p)).orElseThrow(() ->
                new ObjectNotFoundException("Pauta não encontrada"));
    }

    public PautaDTO save(PautaEntity pauta){
        if(rep.findById(pauta.getId()).isPresent()){
            throw new IllegalArgumentException("Pauta com id existente");
        }
        return new PautaDTO(rep.save(pauta));
    }
}
