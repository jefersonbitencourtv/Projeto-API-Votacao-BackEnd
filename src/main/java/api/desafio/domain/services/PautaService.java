package api.desafio.domain.services;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PautaService {
    @Autowired
    private PautaRepository pautaRepository;
    //Retorna lista de pautas
    public ResponsePadrao getPauta() {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(pautaRepository.findAll().stream()
                .map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .collect(Collectors.toList()));
        return responsePadrao;
    }
    //Retorna unica pauta ou exception
    public ResponsePadrao getPautaById(Long id) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(pautaRepository.findById(id).map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .orElseThrow(() ->
                new APIException(APIExceptionEnum.PautaNaoEncontrada)));
        return responsePadrao;
    }
    //Cadastra uma pauta
    public ResponsePadrao inserirPauta(PautaRequest pautaRequest){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        //Valida se campo descrição esta vazio ou nullo
        if(pautaRequest.getDescricao() == null ||pautaRequest.getDescricao().isEmpty()){
            throw new APIException(APIExceptionEnum.DescricaoDeveSerPreenchido);
        }
        //Valida se campo titulo esta vazio ou nulo
        if(pautaRequest.getTitulo() == null ||pautaRequest.getTitulo().isEmpty()){
            throw new APIException(APIExceptionEnum.TituloDeveSerPreenchido);
        }

        PautaEntity pautaEntityBanco = new PautaEntity();
        //Seta entidade com os dados do request
        pautaEntityBanco.setDescricao(pautaRequest.getDescricao());
        pautaEntityBanco.setTitulo(pautaRequest.getTitulo());

        pautaEntityBanco = pautaRepository.save((pautaEntityBanco));

        //Cria PautaDTO com o retorno que veio do banco
        PautaDTO pautaDTO = new PautaDTO(pautaEntityBanco.getId(),pautaEntityBanco.getTitulo(),pautaEntityBanco.getDescricao());

        responsePadrao.setTexto("Pauta criada");
        responsePadrao.setObjeto(pautaDTO);

        return responsePadrao;

    }
}
