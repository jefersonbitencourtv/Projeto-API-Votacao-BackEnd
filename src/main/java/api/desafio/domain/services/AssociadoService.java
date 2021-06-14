package api.desafio.domain.services;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.AssociadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoRepository rep;

    public List<AssociadoDTO> getAssociado(){
        return rep.findAll().stream().map(a -> new AssociadoDTO(a)).collect(Collectors.toList());
    }

    public Optional<AssociadoDTO> getAssociadoById(Long id){
        return rep.findById(id).map(a -> new AssociadoDTO(a));
    }

    public AssociadoResponse save(AssociadoRequest associado){
        //Assert.isNull(associado.getId(), "Não foi possivel criar o associado");
        AssociadoEntity aEntity = new AssociadoEntity();

        aEntity.setCpf(associado.getCpf());

        rep.save(aEntity);

        return new AssociadoResponse();
    }
}
