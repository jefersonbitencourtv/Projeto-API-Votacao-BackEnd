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

    public ResponsePadrao inserirVotacao(VotacaoRequest votacao){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        if(votacao.getIdPauta() == null){
            throw new APIException(APIExceptionEnum.IDPautaDeveSerFornecido);
        }
        if(getVotacaoByIdPautaUsoValidacaoInserirVotacao(votacao.getIdPauta()).isPresent()){
            throw new APIException(APIExceptionEnum.JaExisteVotacao);
        }
        //Validação pauta existe
        pautaService.getPautaById(votacao.getIdPauta());

        VotacaoEntity ve = new VotacaoEntity();
        ve.setDataAbertura(LocalDateTime.now());
        ve.setDuracaoVotacao(votacao.getDuracaoVotacao());
        ve.setIdPauta(votacao.getIdPauta());

        //foi salvo em ve o retorno do banco para funcionar no teste unitario
        //por usar localdatetime.now() ficava com tempo diferente no teste
        //assim os testes nunca eram concluidos com sucesso
        ve = votacaoRepository.save(ve);
        VotacaoDTO dto = new VotacaoDTO(ve.getId(),ve.getIdPauta(),ve.getDuracaoVotacao(),ve.getDataAbertura());

        //responseP.setObjeto(new VotacaoDTO(rep.save(ve)));
        responsePadrao.setObjeto(dto);
        responsePadrao.setTexto("Votacao criada com sucesso");
        return responsePadrao;
    }

    public Optional<VotacaoEntity> getVotacaoByIdPautaUsoValidacaoInserirVotacao(long idPauta){
        return votacaoRepository.findByIdPauta(idPauta);
    }

    public Long getDuracaoVotacaoUsoValidacaoInserirVoto(long id) {
        Optional<VotacaoEntity> findById = votacaoRepository.findById(id);
        return findById.get().getDuracaoVotacao();
    }

    public LocalDateTime getDataAberturaUsoValidacaoInserirVoto(long id) {
        Optional<VotacaoEntity> findById = votacaoRepository.findById(id);
        return findById.get().getDataAbertura();
    }


}

