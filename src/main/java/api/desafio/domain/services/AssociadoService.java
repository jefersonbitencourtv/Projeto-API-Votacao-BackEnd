package api.desafio.domain.services;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.utils.ValidadorCPF;
import api.desafio.exception.CPFInvalidoException;
import api.desafio.exception.CampoInvalidoException;
import api.desafio.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoRepository rep;



    private ResponsePadrao responseP = new ResponsePadrao();

    public ResponsePadrao getAssociado(){
        responseP.setListaObjeto(rep.findAll().stream().map(a -> new AssociadoDTO(a)).collect(Collectors.toList()));
        //return rep.findAll().stream().map(a -> new AssociadoDTO(a)).collect(Collectors.toList());
        return responseP;
    }

    public ResponsePadrao getAssociadoById(Long id){
        responseP.setObjeto(rep.findById(id).map(a -> new AssociadoDTO(a)).orElseThrow(()
        -> new ObjetoNaoEncontradoException("Associado não encontrado")));
         return responseP;
    }

    public ResponsePadrao save(AssociadoRequest associado){
        if(associado.getCpf() == null ||associado.getCpf().isEmpty()){
            throw new CampoInvalidoException("Campo CPF deve ser preenchido");
        }
        /*
        if(associado.getCpf().matches("[A-Z]*[a-z]*")){
           throw new CampoInvalidoException("Campo CPF deve conter apenas números");
        }*/
        if(!(associado.getCpf().matches("[0-9]*"))){
            throw new CampoInvalidoException("Campo CPF deve conter apenas números");
        }
        if(associado.getCpf().length() != 11){
            throw new CampoInvalidoException("CPF deve conter 11 números");
        }
        if(ValidadorCPF.isValidCPF(associado.getCpf()) == false){
            throw new CPFInvalidoException("CPF inválido");
        }




        AssociadoEntity aEntity = new AssociadoEntity();
        aEntity.setCpf(associado.getCpf());
        responseP.setTexto("Associado cadastrado");
        responseP.setObjeto(new AssociadoDTO(rep.save(aEntity)));

        return responseP;
    }
}
