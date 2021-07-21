package api.desafio.domain.services;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.dto.VotoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponseAssociadoDTO;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.domain.response.ApiResponseVotoDTO;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    //Busca uma lista de votos
    public ApiResponseVotoDTO getVoto(){
        ApiResponseVotoDTO apiResponseVotoDTO = new ApiResponseVotoDTO();
        apiResponseVotoDTO.setListaVoto(votoRepository.findAll().stream()
                .map(v -> new VotoDTO(v.getId(), v.getAssociadoEntity().getId(), v.getVotacaoEntity().getId(), v.getVoto()))
                .collect(Collectors.toList()));
        apiResponseVotoDTO.setMensagem("Sucesso!");
        apiResponseVotoDTO.setStatus(HttpStatus.OK);
        return apiResponseVotoDTO;
    }
    //Busca um voto pelo id ou lança uma exception
    public ApiResponseVotoDTO getVotoById(long id){
        ApiResponseVotoDTO apiResponseVotoDTO = new ApiResponseVotoDTO();
        apiResponseVotoDTO.setVoto(
                votoRepository.findById(id)
                        .map(v->new VotoDTO(v.getId(),v.getAssociadoEntity().getId(),v.getVotacaoEntity().getId(),v.getVoto()))
                        .orElseThrow(() ->new APIException(APIExceptionEnum.VOTO_NAO_ENCONTRADO)));
        apiResponseVotoDTO.setMensagem("Sucesso!");
        apiResponseVotoDTO.setStatus(HttpStatus.OK);
        return apiResponseVotoDTO;
    }

    public ApiResponseVotoDTO inserirVoto(VotarRequest votarRequest){

        //Valida se id votação esta nulo ou vazio
        if(votarRequest.getIdVotacao() == null){
            throw new APIException(APIExceptionEnum.VOTACAO_DEVE_SER_FORNECIDA);
        }
        //Valida se id associado esta nulo ou vazio
        if(votarRequest.getIdAssociado() == null){
            throw new APIException(APIExceptionEnum.ASSOCIADO_DEVE_SER_FORNECIDO);
        }
        //Valida se voto esta nulo ou vazio
        if(votarRequest.getVoto() == null || votarRequest.getVoto().isEmpty()){
            throw new APIException(APIExceptionEnum.VOTO_DEVE_SER_FORNECIDO);
        }
        //Valida se voto é sim ou não ou nao, transforma em letra maiscula
        votarRequest.setVoto(votarRequest.getVoto().toUpperCase());
        if(!(votarRequest.getVoto().equals("SIM") ||
                votarRequest.getVoto().equals("NÃO") ||
                votarRequest.getVoto().equals("NAO"))){
            throw new APIException(APIExceptionEnum.VOTO_DEVE_SER_SIM_OU_NAO);
        }
        //Se voto for não, transforma em nao, sem acento til
        if(votarRequest.getVoto().equals("NÃO")){
            votarRequest.setVoto(votarRequest.getVoto().replace("NÃO", "NAO"));
        }

        //Valida existe votação ou lança exception
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = serviceVotacao.getVotacaoById(votarRequest.getIdVotacao());
        //Valida existe associado ou lança exception
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());
        //Cria DTO e entitade votacao e associado
        AssociadoDTO associadoDTO = apiResponseAssociadoDTO.getAssociado();
        AssociadoEntity associadoEntity = associadoDTO.associadoEntity();
        VotacaoDTO votacaoDTO = apiResponseVotacaoDTO.getVotacao();
        VotacaoEntity votacaoEntity = votacaoDTO.VotacaoEntity();
        //Valida se o associado já votou
        if (votoRepository.findByIdAssociadoAndIdVotacao(associadoEntity, votacaoEntity).isPresent()) {
            throw new APIException(APIExceptionEnum.ASSOCIADO_JA_VOTOU);
        }
        //Valida se a votação esta aberta para votar
        if (LocalDateTime.now().isAfter(serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(votarRequest.getIdVotacao()).plusMinutes(serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(votarRequest.getIdVotacao())))){
           throw new APIException(APIExceptionEnum.VOTACAO_NAO_ESTA_DISPONIVEL);
        }

        //Inserir voto no resultado
        serviceResultado.inserirVotoNoResultado(votarRequest.getVoto().toUpperCase(), votarRequest.getIdVotacao());


        //Cria entidade com os dados do request
        VotoEntity votoEntityBanco = new VotoEntity();
        votoEntityBanco.setVoto(votarRequest.getVoto());
        votoEntityBanco.setAssociadoEntity(associadoEntity);
        votoEntityBanco.setVotacaoEntity(votacaoEntity);

        votoEntityBanco = votoRepository.save(votoEntityBanco);

        //Cria VotoDTO com o retorno do banco
        VotoDTO votoDTO = new VotoDTO(votoEntityBanco.getId(), votoEntityBanco.getAssociadoEntity().getId(), votoEntityBanco.getVotacaoEntity().getId(), votoEntityBanco.getVoto());
        ApiResponseVotoDTO apiResponseVotoDTO = new ApiResponseVotoDTO();
        apiResponseVotoDTO.setMensagem("Sucesso ao votar, ID: " + votoDTO.getId());
        apiResponseVotoDTO.setStatus(HttpStatus.CREATED);
        apiResponseVotoDTO.setVoto(votoDTO);
        return apiResponseVotoDTO;

    }
}
