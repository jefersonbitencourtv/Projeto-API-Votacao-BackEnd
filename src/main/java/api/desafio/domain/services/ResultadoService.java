package api.desafio.domain.services;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponseResultadoDTO;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

            //Busca votacao
            Optional<VotacaoDTO> optionalVotacaoDTO = votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(idVotacao);
            VotacaoDTO votacaoDTO = optionalVotacaoDTO.get();
            VotacaoEntity votacaoEntity = votacaoDTO.VotacaoEntity();
            resultadoDTO.setIdVotacaoEntity(votacaoEntity.getId());
            resultadoDTO.setIdPautaEntity(votacaoEntity.getPauta().getId());

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
    public ApiResponseResultadoDTO getResultadoByIdVotacao(Long idVotacao) {
        ApiResponseResultadoDTO apiResponseResultadoDTO = new ApiResponseResultadoDTO();
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(idVotacao);
        apiResponseResultadoDTO.setResultado(resultadoRepository.findByIdVotacao(votacaoEntity)
                .map(r->new ResultadoDTO(r.getId(),r.getVotacaoEntity().getId(),r.getPautaEntity().getId(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()->new APIException(APIExceptionEnum.NAO_EXISTE_RESULTADO_PARA_A_VOTACAO)));
        apiResponseResultadoDTO.setMensagem("Sucesso!");
        apiResponseResultadoDTO.setStatus(HttpStatus.OK);
        return apiResponseResultadoDTO;
    }
    //Método para auxiliar na inserção de um resultado pois não lança exception.
    public Optional<ResultadoDTO> getResultadoByIdVotacaoUsoParaTestarInserirResultado(long idVotacao){
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(idVotacao);
        return resultadoRepository.findByIdVotacao(votacaoEntity)
                .map(r->new ResultadoDTO(r.getId(),r.getVotacaoEntity().getId(),r.getPautaEntity().getId(),r.getQtdSim(),r.getQtdNao()));
    }
    //Busca resultado com id da pauta, ou lança exception
    public ApiResponseResultadoDTO getResultadoByIdPauta(Long idPauta) {
        ApiResponseResultadoDTO apiResponseResultadoDTO = new ApiResponseResultadoDTO();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(idPauta);
        apiResponseResultadoDTO.setResultado(resultadoRepository.findByIdPauta(pautaEntity)
                .map(r -> new ResultadoDTO(r.getId(),r.getVotacaoEntity().getId(),r.getPautaEntity().getId(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
        -> new APIException(APIExceptionEnum.NAO_EXISTE_RESULTADO_PARA_A_PAUTA)));
        apiResponseResultadoDTO.setMensagem("Sucesso!");
        apiResponseResultadoDTO.setStatus(HttpStatus.OK);
        return apiResponseResultadoDTO;
    }
    //Busca uma lista de resultados
    public ApiResponseResultadoDTO getResultado() {
        ApiResponseResultadoDTO apiResponseResultadoDTO = new ApiResponseResultadoDTO();
        apiResponseResultadoDTO.setListaResultado(
                resultadoRepository.findAll().stream()
                        .map(r->new ResultadoDTO(r.getId(),r.getVotacaoEntity().getId(),r.getPautaEntity().getId(),r.getQtdSim(),r.getQtdNao()))
                        .collect(Collectors.toList()));
        apiResponseResultadoDTO.setMensagem("Sucesso!");
        apiResponseResultadoDTO.setStatus(HttpStatus.OK);
        return apiResponseResultadoDTO;
    }
    //Busca resultado pelo id
    public ApiResponseResultadoDTO getResultadoById(Long id){
        ApiResponseResultadoDTO apiResponseResultadoDTO = new ApiResponseResultadoDTO();
        apiResponseResultadoDTO.setResultado(resultadoRepository.findById(id)
                .map(r->new ResultadoDTO(r.getId(),r.getVotacaoEntity().getId(),r.getPautaEntity().getId(),r.getQtdSim(),r.getQtdNao()))
                .orElseThrow(()
                -> new APIException(APIExceptionEnum.RESULTADO_NAO_ENCONTRADO)));
        apiResponseResultadoDTO.setMensagem("Sucesso!");
        apiResponseResultadoDTO.setStatus(HttpStatus.OK);
        return apiResponseResultadoDTO;
    }
}
