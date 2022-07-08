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
    //injecao pra manter uma instancia unica gerenciada pelo spring
    //teria que anotar dai a classe com @Component pra ser possível injetar
    private final ApiResponseAssociadoDTO apiResponseAssociadoDto;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository, ApiCpfService apiCpfService, ApiResponseAssociadoDTO apiResponseAssociadoDto) {
        this.associadoRepository = associadoRepository;
        this.apiCpfService = apiCpfService;
        this.apiResponseAssociadoDto = apiResponseAssociadoDto;
    }

    //Retorna uma lista de associados
    public ApiResponseAssociadoDTO getAssociado(){
        
        //da pra meter um metodo especifico da parte que tu faz o map pra melhorar legibilidade 
        apiResponseAssociadoDTO.setListaAssociado(associadoRepository.findAll().stream().map(a ->
                new AssociadoDTO(a.getCpf(),a.getId()))
                .collect(Collectors.toList()));
        apiResponseAssociadoDTO.setMensagem("Sucesso!");
        apiResponseAssociadoDTO.setStatus(HttpStatus.OK);

        return apiResponseAssociadoDTO;
    }
    //Retorna um unico exception ou nulo
    public ApiResponseAssociadoDTO getAssociadoById(Long id){
        apiResponseAssociadoDTO.setAssociado(associadoRepository.findById(id)
                .map(a -> new AssociadoDTO(a.getCpf(),a.getId()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.ASSOCIADO_NAO_ENCONTRADO)));
        apiResponseAssociadoDTO.setMensagem("Sucesso!");
        apiResponseAssociadoDTO.setStatus(HttpStatus.OK);

        return apiResponseAssociadoDTO;
    }
   public ApiResponseAssociadoDTO inserirAssociado(AssociadoRequest associadoRequest) {
       //coloquei numa variavel pra enxutar
        String cpf = associadoRequest.getCpf();

       //peguei todos aqueles ifs e meti numa método e dei um nome pra ele 
        validaCpf(cpf);

        AssociadoEntity associadoEntityBanco = new AssociadoEntity();
        //Seta entidade com os dados do request
        associadoEntityBanco.setCpf(cpf);
        associadoEntityBanco = associadoRepository.save(associadoEntityBanco);

        //Cria AssociadoDTO com os parametros do banco
        AssociadoDTO associadoDTO = new AssociadoDTO(associadoEntityBanco.getCpf() , associadoEntityBanco.getId());

        apiResponseAssociadoDTO.setMensagem("Associado cadastrado, ID:" + associadoDTO.getId());
        apiResponseAssociadoDTO.setStatus(HttpStatus.CREATED);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        return apiResponseAssociadoDTO;
    }

    //tipo se o cara quer deixar muito legível dá pra fazer isso, pra cada coisa dar um nome pq daí quem for ler vai entender pelo menos o que cad amétodo faz
    //o foda que ficou 5 chamada de método nisso, por isso talvez uma classe separada pra fazer isso seria melhor, pq tu soca tudo nela de método e daí só chama
    //o metodo principal dela e dai ficaria melhor
    public void validaCpf(String cpf) {
        checkIfCpfIsNullOrEmpty(cpf);
        cpfMustHaveOnlyNumbers(cpf);
        cpfMustHave11Characters(cpf);
        checkCpfByExternalApi(cpf);
        checkIfCpfAlreadyExists(cpf);
    }

    private void checkCpfByExternalApi(String cpf) {
        apiCpfService.verificaCpf(cpf);
    }

    private void checkIfCpfAlreadyExists(String cpf) {
        if(associadoRepository.findByCpf(cpf).isPresent()){
            throw new APIException(APIExceptionEnum.CPF_JA_EXISTE_ASSOCIADO);
        }
    }

    private void cpfMustHaveOnlyNumbers(String cpf) {
        if(!(cpf.matches("[0-9]*"))){
            throw new APIException(APIExceptionEnum.CPF_DEVE_CONTER_APENAS_NUMEROS);
        }
    }

    private void cpfMustHave11Characters(String cpf) {
        if(cpf.length() != 11){
            throw new APIException(APIExceptionEnum.CPF_DEVE_CONTER_11_NUMEROS);
        }
    }

    private void checkIfCpfIsNullOrEmpty(String cpf) {
        if(cpf == null ||cpf.isEmpty()){
            throw new APIException(APIExceptionEnum.CPF_DEVE_SER_PREENCHIDO);
        }
    }
}
