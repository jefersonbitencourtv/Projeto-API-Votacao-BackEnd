package api.desafio.domain.services;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PautaService {
    private final PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    //Retorna lista de pautas
    public ApiResponsePautaDTO getPauta() {
        ApiResponsePautaDTO apiResponsePautaDTO = new ApiResponsePautaDTO();
        apiResponsePautaDTO.setListaPauta(pautaRepository.findAll().stream()
                .map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .collect(Collectors.toList()));
        apiResponsePautaDTO.setMensagem("Sucesso!");
        apiResponsePautaDTO.setStatus(HttpStatus.OK);
        return apiResponsePautaDTO;
    }
    //Retorna unica pauta ou exception
    public ApiResponsePautaDTO getPautaById(Long id) {
        ApiResponsePautaDTO apiResponsePautaDTO = new ApiResponsePautaDTO();
        apiResponsePautaDTO.setPauta(pautaRepository.findById(id).map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .orElseThrow(() ->
                new APIException(APIExceptionEnum.PAUTA_NAO_ENCONTRADA)));
        apiResponsePautaDTO.setMensagem("Sucesso!");
        apiResponsePautaDTO.setStatus(HttpStatus.OK);
        return apiResponsePautaDTO;
    }
    //Cadastra uma pauta
    public ApiResponsePautaDTO inserirPauta(PautaRequest pautaRequest){
        ApiResponsePautaDTO apiResponsePautaDTO = new ApiResponsePautaDTO();
        //Valida se campo descrição esta vazio ou nullo
        if(pautaRequest.getDescricao() == null ||pautaRequest.getDescricao().isEmpty()){
            throw new APIException(APIExceptionEnum.DESCRICAO_DEVE_SER_PREENCHIDO);
        }
        //Valida se campo titulo esta vazio ou nulo
        if(pautaRequest.getTitulo() == null ||pautaRequest.getTitulo().isEmpty()){
            throw new APIException(APIExceptionEnum.TITULO_DEVE_SER_PREENCHIDO);
        }

        PautaEntity pautaEntityBanco = new PautaEntity();
        //Seta entidade com os dados do request
        pautaEntityBanco.setDescricao(pautaRequest.getDescricao());
        pautaEntityBanco.setTitulo(pautaRequest.getTitulo());

        pautaEntityBanco = pautaRepository.save((pautaEntityBanco));

        //Cria PautaDTO com o retorno que veio do banco
        PautaDTO pautaDTO = new PautaDTO(pautaEntityBanco.getId(),pautaEntityBanco.getTitulo(),pautaEntityBanco.getDescricao());

        apiResponsePautaDTO.setMensagem("Pauta criada, ID:" + pautaDTO.getId());
        apiResponsePautaDTO.setStatus(HttpStatus.CREATED);
        apiResponsePautaDTO.setPauta(pautaDTO);
        return apiResponsePautaDTO;

    }
}
