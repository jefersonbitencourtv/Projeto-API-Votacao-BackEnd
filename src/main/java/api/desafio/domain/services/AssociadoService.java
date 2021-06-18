package api.desafio.domain.services;
import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.utils.ValidadorCPF;
import api.desafio.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoRepository associadoRepository;

    //Retorna uma lista de associados
    public ResponsePadrao getAssociado(){
        ResponsePadrao responsePadrao = new ResponsePadrao();
         responsePadrao.setListaObjeto(associadoRepository.findAll().stream().map(a ->
                new AssociadoDTO(a.getCpf(),a.getId()))
                .collect(Collectors.toList()));

        return responsePadrao;
    }
    //Retorna um unico exception ou nulo
    public ResponsePadrao getAssociadoById(Long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(associadoRepository.findById(id)
                .map(a -> new AssociadoDTO(a.getCpf(),a.getId()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.AssociadoNaoEncontrado)));
        return responsePadrao;
    }
    public ResponsePadrao inserirAssociado(AssociadoRequest associadoRequest){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        //Valida cpf nulo ou vazio
        if(associadoRequest.getCpf() == null ||associadoRequest.getCpf().isEmpty()){
            throw new APIException(APIExceptionEnum.CpfDeveSerPreenchido);
        }
        //Valida cpf com apenas números
        if(!(associadoRequest.getCpf().matches("[0-9]*"))){
            throw new APIException(APIExceptionEnum.CpfDeveConterApenasNumeros);
        }
        //Valida cpf com exatamente 11 números
        if(associadoRequest.getCpf().length() != 11){
            throw new APIException(APIExceptionEnum.CpfDeveConter11Numeros);
        }
        //Valida cpf se é valido, bibilioteca utils ValidadorCPF
        if(ValidadorCPF.isValidCPF(associadoRequest.getCpf()) == false) {
            throw new APIException(APIExceptionEnum.CPFInvalido);
        }
        //Valida se já existe aquele cpf no banco
        if(associadoRepository.findByCpf(associadoRequest.getCpf()).isPresent()){
            throw new APIException(APIExceptionEnum.CpfJáExisteAssociado);
        }

        AssociadoEntity associadoEntityBanco = new AssociadoEntity();
        //Seta entidade com os dados do request
        associadoEntityBanco.setCpf(associadoRequest.getCpf());
        associadoEntityBanco = associadoRepository.save(associadoEntityBanco);

        //Cria AssociadoDTO com os parametros do banco
        AssociadoDTO associadoDTO = new AssociadoDTO(associadoEntityBanco.getCpf() , associadoEntityBanco.getId());

        responsePadrao.setTexto("Associado cadastrado");
        responsePadrao.setObjeto(associadoDTO);

        return responsePadrao;
    }
}
