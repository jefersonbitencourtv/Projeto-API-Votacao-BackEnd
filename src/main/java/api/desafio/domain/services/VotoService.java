package api.desafio.domain.services;

import api.desafio.domain.entities.VotoEntity;
import api.desafio.domain.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
@Service
public class VotoService {
    @Autowired
    private VotoRepository rep;
    @Autowired
    private VotacaoService serviceVotacao;
    public Iterable<VotoEntity> getVoto(){
        return rep.findAll();
    }

    public Optional<VotoEntity> getVotoById(Long id){
        return rep.findById(id);
    }

    public VotoEntity save(VotoEntity voto) throws Exception {
        if (!(serviceVotacao.getVotacaoById(voto.getIdVotacao()).isPresent())) {
            throw new Exception("Votacao Não Existe");
        }
        ;
        if (rep.findByIdAssociado(voto.getIdAssociado()).isPresent()) {
            throw new Exception("Associado já votou");
        }
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAbertura(voto.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacao(voto.getIdVotacao())))){
           throw new Exception("Essa votação não está mais disponivel");
        }
        serviceVotacao.updateVoto(voto.getVoto().toUpperCase(), voto.getIdVotacao());
        return rep.save(voto);

    }
}
