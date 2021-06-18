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

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoRepository associadoRepository;


    public ResponsePadrao getAssociado(){
        ResponsePadrao responsePadrao = new ResponsePadrao();
         responsePadrao.setListaObjeto(associadoRepository.findAll().stream().map(a ->
                new AssociadoDTO(a.getCpf(),a.getId()))
                .collect(Collectors.toList()));

        return responsePadrao;
    }

    public ResponsePadrao getAssociadoById(Long id){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(associadoRepository.findById(id)
                .map(a -> new AssociadoDTO(a.getCpf(),a.getId()))
                .orElseThrow(()-> new APIException(APIExceptionEnum.AssociadoNaoEncontrado)));
        return responsePadrao;
    }

    public ResponsePadrao inserirAssociado(AssociadoRequest associado){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        if(associado.getCpf() == null ||associado.getCpf().isEmpty()){
            throw new APIException(APIExceptionEnum.CpfDeveSerPreenchido);
        }
        if(!(associado.getCpf().matches("[0-9]*"))){
            throw new APIException(APIExceptionEnum.CpfDeveConterApenasNumeros);
        }
        if(associado.getCpf().length() != 11){
            throw new APIException(APIExceptionEnum.CpfDeveConter11Numeros);
        }
        if(ValidadorCPF.isValidCPF(associado.getCpf()) == false) {
            throw new APIException(APIExceptionEnum.CPFInvalido);
        }
        if(associadoRepository.findByCpf(associado.getCpf()).isPresent()){
            throw new APIException(APIExceptionEnum.CpfJáExisteAssociado);
        }


        AssociadoEntity aEntity = new AssociadoEntity();
        aEntity.setCpf(associado.getCpf());


        associadoRepository.save(aEntity);
        AssociadoDTO ab = new AssociadoDTO(aEntity.getCpf() , aEntity.getId());
        responsePadrao.setTexto("Associado cadastrado");
        responsePadrao.setObjeto(ab);

        return responsePadrao;
    }
}
