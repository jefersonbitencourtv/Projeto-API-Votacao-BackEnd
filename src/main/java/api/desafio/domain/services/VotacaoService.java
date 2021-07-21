package api.desafio.domain.services;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ApiResponseVotacaoDTO getVotacao() {
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();
        apiResponseVotacaoDTO.setListaVotacao(votacaoRepository.findAll()
                .stream()
                .map(v -> new VotacaoDTO(v.getId(),v.getPauta().getId(),v.getDuracaoVotacao(),v.getDataAbertura()))
                .collect(Collectors.toList()));
        apiResponseVotacaoDTO.setMensagem("Sucesso!");
        apiResponseVotacaoDTO.setStatus(HttpStatus.OK);
        return apiResponseVotacaoDTO;
    }

    public Optional<VotacaoDTO> getVotacaoByIdUsoValidacaoInsercaoResultado(long id) {
        return votacaoRepository.findById(id)
                .map(v -> new VotacaoDTO(v.getId(),v.getPauta().getId(),v.getDuracaoVotacao(),v.getDataAbertura()));
    }

    public ApiResponseVotacaoDTO getVotacaoById(long id) {
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();
        apiResponseVotacaoDTO.setVotacao(votacaoRepository.findById(id)
                .map(v -> new VotacaoDTO(v.getId(),v.getPauta().getId(),v.getDuracaoVotacao(),v.getDataAbertura()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.VOTACAO_NAO_ENCONTRADA)));
        apiResponseVotacaoDTO.setMensagem("Sucesso!");
        apiResponseVotacaoDTO.setStatus(HttpStatus.OK);
        return apiResponseVotacaoDTO;
    }

    public ApiResponseVotacaoDTO inserirVotacao(VotacaoRequest votacaoRequest){
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();
        //Caso duraçãoVotacão vazio ou nulo, seta valor default 1
        if (votacaoRequest.getDuracaoVotacao() == null  || votacaoRequest.getDuracaoVotacao() == 0) {
            votacaoRequest.setDuracaoVotacao(1L);
        }
        //Valida se pauta esta nulo
        if(votacaoRequest.getIdPauta() == null){
            throw new APIException(APIExceptionEnum.ID_PAUTA_DEVE_SER_FORNECIDO);
        }
        //Valida se já existe votação para a pauta
        if(getVotacaoByIdPautaUsoValidacaoInserirVotacao(votacaoRequest.getIdPauta()).isPresent()){
            throw new APIException(APIExceptionEnum.JA_EXISTE_VOTACAO);
        }
        //Validação pauta existe
        ApiResponsePautaDTO apiResponsePautaDTO = new ApiResponsePautaDTO();
        apiResponsePautaDTO = pautaService.getPautaById(votacaoRequest.getIdPauta());
        //Cria PautaDTO e PautaEntity
        PautaDTO pautaDTO = apiResponsePautaDTO.getPauta();
        PautaEntity pautaEntity = pautaDTO.PautaEntity();

        VotacaoEntity VotacaoEntityBanco = new VotacaoEntity();
        //Seta entidade com os dados do request
        VotacaoEntityBanco.setDataAbertura(LocalDateTime.now());
        VotacaoEntityBanco.setDuracaoVotacao(votacaoRequest.getDuracaoVotacao());
        VotacaoEntityBanco.setPauta(pautaEntity);

        VotacaoEntityBanco = votacaoRepository.save(VotacaoEntityBanco);
        //Cria VotacaoDTO com o retorno do banco
        VotacaoDTO votacaoDTO = new VotacaoDTO(VotacaoEntityBanco.getId(),VotacaoEntityBanco.getPauta().getId(),VotacaoEntityBanco.getDuracaoVotacao(),VotacaoEntityBanco.getDataAbertura());

        apiResponseVotacaoDTO.setVotacao(votacaoDTO);
        apiResponseVotacaoDTO.setMensagem("Votacao criada com sucesso, Id"+ votacaoDTO.getId());
        apiResponseVotacaoDTO.setStatus(HttpStatus.CREATED);
        return apiResponseVotacaoDTO;
    }
    //Método para auxilair para inserção votação, busca se já há uma pauta no banco
    public Optional<VotacaoEntity> getVotacaoByIdPautaUsoValidacaoInserirVotacao(long idPauta){
        PautaEntity aa = new PautaEntity();
        aa.setId(idPauta);
        return votacaoRepository.findByPauta(aa);
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

