package api.desafio.domain.services;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotoService {
    @Autowired
    private VotoRepository rep;
    @Autowired
    private VotacaoService serviceVotacao;
    @Autowired
    private ResultadoService serviceResultado;
    @Autowired
    private AssociadoService serviceAssociado;

    public List<VotarDTO> getVoto(){
        return rep.findAll().stream()
                .map(v->new VotarDTO(v)).collect(Collectors.toList());
    }

    public VotarDTO getVotoById(long id){

        return rep.findById(id).map(v->new VotarDTO(v)).orElseThrow(()->new ObjectNotFoundException("Voto não encontrado"));
    }

    public VotarDTO save(VotarEntity voto) throws Exception {
        serviceVotacao.getVotacaoById(voto.getIdVotacao());
        serviceAssociado.getAssociadoById(voto.getIdAssociado());

        if (rep.findByIdAssociadoAndIdVotacao(voto.getIdAssociado(), voto.getIdVotacao()).isPresent()) {
            throw new Exception("Associado já votou");
        }
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAbertura(voto.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacao(voto.getIdVotacao())))){
           throw new Exception("Essa votação não está mais disponivel");
        }

        serviceResultado.updateVoto(voto.getVoto().toUpperCase(), voto.getIdVotacao());
        return new VotarDTO(rep.save(voto));

    }
}
