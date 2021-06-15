package api.desafio.domain.services;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.AssociadoJaVotouException;
import api.desafio.exception.CampoInvalidoException;
import api.desafio.exception.ObjetoNaoEncontradoException;
import api.desafio.exception.VotacaoExpiradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
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

    private ResponsePadrao responseP = new ResponsePadrao();
    public ResponsePadrao getVoto(){
        responseP.setListaObjeto(rep.findAll().stream()
                .map(v->new VotarDTO(v)).collect(Collectors.toList()));
        return responseP;
    }

    public ResponsePadrao getVotoById(long id){

        responseP.setObjeto(
                rep.findById(id).map(v->new VotarDTO(v)).orElseThrow(()
                        ->new ObjetoNaoEncontradoException("Voto não encontrado")));
        return responseP;
    }

    public ResponsePadrao save(VotarRequest voto){
        if(voto.getIdVotacao() == null){
            throw new CampoInvalidoException("Votacação deve ser fornecida");
        }
        if(voto.getIdAssociado() == null){
            throw new CampoInvalidoException("Associado deve ser fornecido");
        }
        if(voto.getVoto() == null || voto.getVoto().isEmpty()){
            throw new CampoInvalidoException("Voto deve ser fornecido");
        }

        voto.setVoto(voto.getVoto().toUpperCase());
        if(!(voto.getVoto().equals("SIM") ||
                voto.getVoto().equals("NÃO") ||
                voto.getVoto().equals("NAO"))){
            throw new CampoInvalidoException("Voto deve ser apenas sim ou não");
        }
        if(voto.getVoto().equals("NÃO")){
            voto.setVoto(voto.getVoto().replaceAll("NÃO", "NAO"));
        }
        serviceVotacao.getVotacaoById(voto.getIdVotacao());
        serviceAssociado.getAssociadoById(voto.getIdAssociado());

        if (rep.findByIdAssociadoAndIdVotacao(voto.getIdAssociado(), voto.getIdVotacao()).isPresent()) {
            throw new AssociadoJaVotouException("Associado já votou");
        }
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAbertura(voto.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacao(voto.getIdVotacao())))){
           throw new VotacaoExpiradaException("Essa votação não está mais disponivel");
        }

        serviceResultado.updateVoto(voto.getVoto().toUpperCase(), voto.getIdVotacao());

        VotarEntity ve = new VotarEntity();
        ve.setVoto(voto.getVoto());
        ve.setIdAssociado(voto.getIdAssociado());
        ve.setIdVotacao(voto.getIdVotacao());

        responseP.setObjeto(new VotarDTO(rep.save(ve)));
        responseP.setTexto("Voto criado com sucesso");
        return responseP;

    }
}
