package api.desafio.domain.services;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.VotarRepository;
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
    private VotarRepository votarRepository;
    @Autowired
    private VotacaoService serviceVotacao;
    @Autowired
    private ResultadoService serviceResultado;
    @Autowired
    private AssociadoService serviceAssociado;

    //Busca uma lista de votos
    public ResponsePadrao getVoto(){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(votarRepository.findAll().stream()
                .map(v->new VotarDTO(v.getId(),v.getIdAssociado(),v.getIdVotacao(),v.getVoto()))
                .collect(Collectors.toList()));
        return responsePadrao;
    }
    //Busca um voto pelo id ou lança uma exception
    public ResponsePadrao getVotoById(long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(
                votarRepository.findById(id)
                        .map(v->new VotarDTO(v.getId(),v.getIdAssociado(),v.getIdVotacao(),v.getVoto()))
                        .orElseThrow(() ->new APIException(APIExceptionEnum.VotoNaoEncontrado)));
        return responsePadrao;
    }

    public ResponsePadrao inserirVoto(VotarRequest votarRequest){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        //Valida se id votação esta nulo ou vazio
        if(votarRequest.getIdVotacao() == null){
            throw new APIException(APIExceptionEnum.VotacaoDeveSerFornecida);
        }
        //Valida se id associado esta nulo ou vazio
        if(votarRequest.getIdAssociado() == null){
            throw new APIException(APIExceptionEnum.AssociadoDeveSerFornecido);
        }
        //Valida se voto esta nulo ou vazio
        if(votarRequest.getVoto() == null || votarRequest.getVoto().isEmpty()){
            throw new APIException(APIExceptionEnum.VotoDeveSerFornecido);
        }
        //Valida se voto é sim ou não ou nao, transforma em letra maiscula
        votarRequest.setVoto(votarRequest.getVoto().toUpperCase());
        if(!(votarRequest.getVoto().equals("SIM") ||
                votarRequest.getVoto().equals("NÃO") ||
                votarRequest.getVoto().equals("NAO"))){
            throw new APIException(APIExceptionEnum.VotoDeveSerSimOuNao);
        }
        //Se voto for não, transforma em nao, sem acento til
        if(votarRequest.getVoto().equals("NÃO")){
            votarRequest.setVoto(votarRequest.getVoto().replace("NÃO", "NAO"));
        }
        //Valida existe votação ou lança exception
        serviceVotacao.getVotacaoById(votarRequest.getIdVotacao());
        //Valida existe associado ou lança exception
        serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());

        //Valida se o associado já votou
        if (votarRepository.findByIdAssociadoAndIdVotacao(votarRequest.getIdAssociado(), votarRequest.getIdVotacao()).isPresent()) {
            throw new APIException(APIExceptionEnum.AssociadoJaVotou);
        }
        //Valida se a votação esta aberta para votar
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(votarRequest.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(votarRequest.getIdVotacao())))){
           throw new APIException(APIExceptionEnum.VotacaoNaoEstaDisponivel);
        }

        //Inserir voto no resultado
        serviceResultado.inserirVotoNoResultado(votarRequest.getVoto().toUpperCase(), votarRequest.getIdVotacao());

        //Cria entidade com os dados do request
        VotarEntity votarEntityBanco = new VotarEntity();
        votarEntityBanco.setVoto(votarRequest.getVoto());
        votarEntityBanco.setIdAssociado(votarRequest.getIdAssociado());
        votarEntityBanco.setIdVotacao(votarRequest.getIdVotacao());

        votarEntityBanco = votarRepository.save(votarEntityBanco);

        //Cria VotarDTO com o retorno do banco
        VotarDTO dto = new VotarDTO(votarEntityBanco.getId(),votarEntityBanco.getIdAssociado(),votarEntityBanco.getIdVotacao(),votarEntityBanco.getVoto());

        responsePadrao.setObjeto(dto);
        responsePadrao.setTexto("Voto criado com sucesso");
        return responsePadrao;

    }
}
