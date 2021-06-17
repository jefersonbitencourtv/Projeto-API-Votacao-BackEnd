package api.desafio.domain.services;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private VotacaoService serviceVotacao;
    @Autowired
    private ResultadoService serviceResultado;
    @Autowired
    private AssociadoService serviceAssociado;


    public ResponsePadrao getVoto(){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(votoRepository.findAll().stream()
                .map(v->new VotarDTO(v.getId(),v.getIdAssociado(),v.getIdVotacao(),v.getVoto()))
                .collect(Collectors.toList()));
        return responsePadrao;
    }

    public ResponsePadrao getVotoById(long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(
                votoRepository.findById(id)
                        .map(v->new VotarDTO(v.getId(),v.getIdAssociado(),v.getIdVotacao(),v.getVoto()))
                        .orElseThrow(() ->new APIException(APIExceptionEnum.VotoNaoEncontrado)));
        return responsePadrao;
    }

    public ResponsePadrao inserirVoto(VotarRequest voto){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        if(voto.getIdVotacao() == null){
            throw new APIException(APIExceptionEnum.VotacaoDeveSerFornecida);
        }
        if(voto.getIdAssociado() == null){
            throw new APIException(APIExceptionEnum.AssociadoDeveSerFornecido);
        }
        if(voto.getVoto() == null || voto.getVoto().isEmpty()){
            throw new APIException(APIExceptionEnum.VotoDeveSerFornecido);
        }

        voto.setVoto(voto.getVoto().toUpperCase());
        if(!(voto.getVoto().equals("SIM") ||
                voto.getVoto().equals("NÃO") ||
                voto.getVoto().equals("NAO"))){
            throw new APIException(APIExceptionEnum.VotoDeveSerSimOuNao);
        }
        if(voto.getVoto().equals("NÃO")){
            voto.setVoto(voto.getVoto().replace("NÃO", "NAO"));
        }
        serviceVotacao.getVotacaoById(voto.getIdVotacao());
        serviceAssociado.getAssociadoById(voto.getIdAssociado());

        if (votoRepository.findByIdAssociadoAndIdVotacao(voto.getIdAssociado(), voto.getIdVotacao()).isPresent()) {
            throw new APIException(APIExceptionEnum.AssociadoJaVotou);
        }
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(voto.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(voto.getIdVotacao())))){
           throw new APIException(APIExceptionEnum.VotacaoNaoEstaDisponivel);
        }

        serviceResultado.inserirVotoNoResultado(voto.getVoto().toUpperCase(), voto.getIdVotacao());

        VotarEntity ve = new VotarEntity();
        ve.setVoto(voto.getVoto());
        ve.setIdAssociado(voto.getIdAssociado());
        ve.setIdVotacao(voto.getIdVotacao());
        votoRepository.save(ve);
        VotarDTO dto = new VotarDTO(ve.getId(),ve.getIdAssociado(),ve.getIdVotacao(),ve.getVoto());
        responsePadrao.setObjeto(dto);
        responsePadrao.setTexto("Voto criado com sucesso");
        return responsePadrao;

    }
}
