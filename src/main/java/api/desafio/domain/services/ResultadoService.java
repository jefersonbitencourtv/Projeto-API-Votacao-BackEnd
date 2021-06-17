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

    public void inserirVotoNoResultado(String voto, long idVotacao){
        ResultadoDTO resultadoDTO;
        //Se ja existe resultado no banco para a votacao
        if(getResultadoByIdVotacaoUsoParaTestarInserirResultado(idVotacao).isPresent()){
            Optional<ResultadoDTO> rE = getResultadoByIdVotacaoUsoParaTestarInserirResultado(idVotacao);
            resultadoDTO = rE.get();
        //Se nao cria resultado no banco para a votacao
        }else {
            resultadoDTO = new ResultadoDTO();
            resultadoDTO.setIdVotacao(idVotacao);

            Optional<VotacaoDTO> dto = votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(idVotacao);
            VotacaoDTO vDTO = dto.get();
            VotacaoEntity vEntity = vDTO.VotacaoEntity();
            //vEnt.getId();
            resultadoDTO.setIdPauta(vEntity.getIdPauta());

        }


            if(voto.equals("SIM")) {
                int i = resultadoDTO.getQtdSim();
                resultadoDTO.setQtdSim(i+1);
            }
            if(voto.equals("NAO")) {
                int i = resultadoDTO.getQtdNao();
                resultadoDTO.setQtdNao(i+1);
            }

            ResultadoEntity re = resultadoDTO.ResultadoEntity();
            resultadoRepository.save(re);

        }

    public ResponsePadrao getResultadoByIdVotacao(Long idVotacao) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findByIdVotacao(idVotacao)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()->new APIException(APIExceptionEnum.NaoExisteResultadoParaAVotacao)));
        return responsePadrao;
    }

    public Optional<ResultadoDTO> getResultadoByIdVotacaoUsoParaTestarInserirResultado(long idVotacao){
        return resultadoRepository.findByIdVotacao(idVotacao)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdPauta(),r.getQtdSim(),r.getQtdNao()));
    }

    public ResponsePadrao getResultadoByIdPauta(Long idPauta) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findByIdPauta(idPauta)
                .map(r -> new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
        -> new APIException(APIExceptionEnum.NaoExisteResultadoParaAPauta)));
        return responsePadrao;
    }

    public ResponsePadrao getResultado() {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(
                resultadoRepository.findAll()
                        .stream()
                        .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                        .collect(Collectors.toList()));
        return responsePadrao;
    }

    public ResponsePadrao getResultadoById(Long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(resultadoRepository.findById(id)
                .map(r->new ResultadoDTO(r.getId(),r.getIdVotacao(),r.getIdVotacao(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
                -> new APIException(APIExceptionEnum.ResultadoNaoEncontrado)));
        return responsePadrao;
    }
}
