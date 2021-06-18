package api.desafio.domain.services;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoService {
    @Autowired
    private ResultadoRepository resultadoRepository;
    @Autowired
    private VotacaoService votacaoService;
    public void inserirVotoNoResultado(String voto, long idVotacao) {
        ResultadoDTO resultadoDTO;
        //Se ja existe resultado no banco para a votacao vai criar um ResultadoDTO com os dados do banco
        if (getResultadoByIdVotacaoUsoParaTestarInserirResultado(idVotacao).isPresent()) {
            Optional<ResultadoDTO> rE = getResultadoByIdVotacaoUsoParaTestarInserirResultado(idVotacao);
            resultadoDTO = rE.get();
            //Se nao existe, cria um novo ResultadoDTO
        } else {
            resultadoDTO = new ResultadoDTO();
            resultadoDTO.setIdVotacao(idVotacao);

            //Busca votacao
            Optional<VotacaoDTO> optionalVotacaoDTO = votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(idVotacao);
            VotacaoDTO votacaoDTO = optionalVotacaoDTO.get();
            VotacaoEntity votacaoEntity = votacaoDTO.VotacaoEntity();
            resultadoDTO.setIdPauta(votacaoEntity.getIdPauta());

        }

        //Se voto for sim soma na quantidade de resultados sim
        if (voto.equals("SIM")) {
            int i = resultadoDTO.getQtdSim();
            resultadoDTO.setQtdSim(i + 1);
        }
        //Se voto for nao soma na quantidade de resultados nao
        if (voto.equals("NAO")) {
            int i = resultadoDTO.getQtdNao();
            resultadoDTO.setQtdNao(i + 1);
        }
        //Cria entidade de resultado usando ResultadoDTO
        ResultadoEntity resultadoEntity = resultadoDTO.ResultadoEntity();

        resultadoRepository.save(resultadoEntity);

    }
    //Busca resultado pelo id da votação, ou lança exception
    public ResponsePadrao getResultadoByIdVotacao(Long idVotacao) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findByIdVotacao(idVotacao)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdPauta(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()->new APIException(APIExceptionEnum.NaoExisteResultadoParaAVotacao)));
        return responsePadrao;
    }
    //Método para auxiliar na inserção de um resultado pois não lança exception.
    public Optional<ResultadoDTO> getResultadoByIdVotacaoUsoParaTestarInserirResultado(long idVotacao){
        return resultadoRepository.findByIdVotacao(idVotacao)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdPauta(),r.getQtdSim(),r.getQtdNao()));
    }
    //Busca resultado com id da pauta, ou lança exception
    public ResponsePadrao getResultadoByIdPauta(Long idPauta) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findByIdPauta(idPauta)
                .map(r -> new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdPauta(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
        -> new APIException(APIExceptionEnum.NaoExisteResultadoParaAPauta)));
        return responsePadrao;
    }
    //Busca uma lista de resultados
    public ResponsePadrao getResultado() {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(
                resultadoRepository.findAll().stream()
                        .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                        .collect(Collectors.toList()));
        return responsePadrao;
    }
    //Busca resultado pelo id
    public ResponsePadrao getResultadoById(Long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findById(id)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
                -> new APIException(APIExceptionEnum.ResultadoNaoEncontrado)));
        return responsePadrao;
    }
}
