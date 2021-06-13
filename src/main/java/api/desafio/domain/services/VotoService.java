package api.desafio.domain.services;

import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
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

    public Iterable<VotarEntity> getVoto(){
        return rep.findAll();
    }

    public Optional<VotarEntity> getVotoById(Long id){
        return rep.findById(id);
    }

    public VotarEntity save(VotarEntity voto) throws Exception {
        if (!(serviceVotacao.getVotacaoById(voto.getIdVotacao()).isPresent())) {
            throw new Exception("Votacao Não Existe");
        }
        if(!(serviceAssociado.getAssociadoById(voto.getIdAssociado()).isPresent())){
            throw new Exception("Associado não existe");
        }
        if (rep.findByIdAssociadoAndIdVotacao(voto.getIdAssociado(), voto.getIdVotacao()).isPresent()) {
            throw new Exception("Associado já votou");
        }
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAbertura(voto.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacao(voto.getIdVotacao())))){
           throw new Exception("Essa votação não está mais disponivel");
        }

        serviceResultado.updateVoto(voto.getVoto().toUpperCase(), voto.getIdVotacao());
        return rep.save(voto);

    }
}
