package api.desafio.domain.services;
import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ApiResponseAssociadoDTO;
import api.desafio.domain.services.apiCpf.ApiCpfService;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class AssociadoService {
    private final AssociadoRepository associadoRepository;
    private final ApiCpfService apiCpfService;

    public AssociadoService(AssociadoRepository associadoRepository, ApiCpfService apiCpfService) {
        this.associadoRepository = associadoRepository;
        this.apiCpfService = apiCpfService;
    }

    //Retorna uma lista de associados
    public ApiResponseAssociadoDTO getAssociado(){
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        apiResponseAssociadoDTO.setListaAssociado(associadoRepository.findAll().stream().map(a ->
                new AssociadoDTO(a.getCpf(),a.getId()))
                .collect(Collectors.toList()));
        apiResponseAssociadoDTO.setMensagem("Sucesso!");
        apiResponseAssociadoDTO.setStatus(HttpStatus.OK);

        return apiResponseAssociadoDTO;
    }
    //Retorna um unico exception ou nulo
    public ApiResponseAssociadoDTO getAssociadoById(Long id){
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        apiResponseAssociadoDTO.setAssociado(associadoRepository.findById(id)
                .map(a -> new AssociadoDTO(a.getCpf(),a.getId()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.ASSOCIADO_NAO_ENCONTRADO)));
        apiResponseAssociadoDTO.setMensagem("Sucesso!");
        apiResponseAssociadoDTO.setStatus(HttpStatus.OK);

        return apiResponseAssociadoDTO;
    }
    public ApiResponseAssociadoDTO inserirAssociado(AssociadoRequest associadoRequest) throws IOException {
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        //Valida cpf nulo ou vazio
        if(associadoRequest.getCpf() == null ||associadoRequest.getCpf().isEmpty()){
            throw new APIException(APIExceptionEnum.CPF_DEVE_SER_PREENCHIDO);
        }
        //Valida cpf com apenas números
        if(!(associadoRequest.getCpf().matches("[0-9]*"))){
            throw new APIException(APIExceptionEnum.CPF_DEVE_CONTER_APENAS_NUMEROS);
        }
        //Valida cpf com exatamente 11 números
        if(associadoRequest.getCpf().length() != 11){
            throw new APIException(APIExceptionEnum.CPF_DEVE_CONTER_11_NUMEROS);
        }
        //Valida cpf se é valido, bibilioteca utils ValidadorCPF
        /*if(ValidadorCPF.isValidCPF(associadoRequest.getCpf()) == false) {
            throw new APIException(APIExceptionEnum.CPF_INVALIDO);
        }*/
        //Valida cpf se é valido, api externa

        apiCpfService.verificaCpf(associadoRequest.getCpf());

        //Valida se já existe aquele cpf no banco
        if(associadoRepository.findByCpf(associadoRequest.getCpf()).isPresent()){
            throw new APIException(APIExceptionEnum.CPF_JA_EXISTE_ASSOCIADO);
        }

        AssociadoEntity associadoEntityBanco = new AssociadoEntity();
        //Seta entidade com os dados do request
        associadoEntityBanco.setCpf(associadoRequest.getCpf());
        associadoEntityBanco = associadoRepository.save(associadoEntityBanco);

        //Cria AssociadoDTO com os parametros do banco
        AssociadoDTO associadoDTO = new AssociadoDTO(associadoEntityBanco.getCpf() , associadoEntityBanco.getId());

        apiResponseAssociadoDTO.setMensagem("Associado cadastrado, ID:" + associadoDTO.getId());
        apiResponseAssociadoDTO.setStatus(HttpStatus.CREATED);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        return apiResponseAssociadoDTO;
    }
}
