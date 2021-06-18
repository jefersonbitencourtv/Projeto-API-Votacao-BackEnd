package api.desafio.domain.services;

import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotacaoService {

    @Autowired
    private VotacaoRepository votacaoRepository;
    @Autowired
    private PautaService pautaService;

    public ResponsePadrao getVotacao() {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(votacaoRepository.findAll()
                .stream()
                .map(v -> new VotacaoDTO(v.getId(),v.getIdPauta(),v.getDuracaoVotacao(),v.getDataAbertura()))
                .collect(Collectors.toList()));
        return responsePadrao;
    }

    public Optional<VotacaoDTO> getVotacaoByIdUsoValidacaoInsercaoResultado(long id) {
        return votacaoRepository.findById(id)
                .map(v -> new VotacaoDTO(v.getId(),v.getIdPauta(),v.getDuracaoVotacao(),v.getDataAbertura()));
    }

    public ResponsePadrao getVotacaoById(long id) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(votacaoRepository.findById(id)
                .map(v -> new VotacaoDTO(v.getId(),v.getIdPauta(),v.getDuracaoVotacao(),v.getDataAbertura()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.VotacaoNaoEncontrada)));
        return responsePadrao;
    }

    public ResponsePadrao inserirVotacao(VotacaoRequest votacaoRequest){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        //Caso duraçãoVotacão vazio ou nulo, seta valor default 1
        if (votacaoRequest.getDuracaoVotacao() == null  || votacaoRequest.getDuracaoVotacao() == 0) {
            votacaoRequest.setDuracaoVotacao(1L);
        }
        //Valida se pauta esta nulo
        if(votacaoRequest.getIdPauta() == null){
            throw new APIException(APIExceptionEnum.IDPautaDeveSerFornecido);
        }
        //Valida se já existe votação para a pauta
        if(getVotacaoByIdPautaUsoValidacaoInserirVotacao(votacaoRequest.getIdPauta()).isPresent()){
            throw new APIException(APIExceptionEnum.JaExisteVotacao);
        }
        //Validação pauta existe
        pautaService.getPautaById(votacaoRequest.getIdPauta());


        VotacaoEntity VotacaoEntityBanco = new VotacaoEntity();
        //Seta entidade com os dados do request
        VotacaoEntityBanco.setDataAbertura(LocalDateTime.now());
        VotacaoEntityBanco.setDuracaoVotacao(votacaoRequest.getDuracaoVotacao());
        VotacaoEntityBanco.setIdPauta(votacaoRequest.getIdPauta());

        VotacaoEntityBanco = votacaoRepository.save(VotacaoEntityBanco);
        //Cria VotacaoDTO com o retorno do banco
        VotacaoDTO votacaoDTO = new VotacaoDTO(VotacaoEntityBanco.getId(),VotacaoEntityBanco.getIdPauta(),VotacaoEntityBanco.getDuracaoVotacao(),VotacaoEntityBanco.getDataAbertura());

        responsePadrao.setObjeto(votacaoDTO);
        responsePadrao.setTexto("Votacao criada com sucesso");
        return responsePadrao;
    }
    //Método para auxilair para inserção votação, busca se já há uma pauta no banco
    public Optional<VotacaoEntity> getVotacaoByIdPautaUsoValidacaoInserirVotacao(long idPauta){
        return votacaoRepository.findByIdPauta(idPauta);
    }
    //Método para auxiliar na inserção de um voto, busca a data de duração da votação
    public Long getDuracaoVotacaoUsoValidacaoInserirVoto(long id) {
        Optional<VotacaoEntity> findById = votacaoRepository.findById(id);
        return findById.get().getDuracaoVotacao();
    }
    //Método para auxiliar na inserção de um voto, busca a data de abertura da votação
    public LocalDateTime getDataAberturaUsoValidacaoInserirVoto(long id) {
        Optional<VotacaoEntity> findById = votacaoRepository.findById(id);
        return findById.get().getDataAbertura();
    }


}

