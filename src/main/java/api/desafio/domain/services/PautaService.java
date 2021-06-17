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


    public ResponsePadrao getPauta() {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setListaObjeto(pautaRepository.findAll()
                .stream()
                .map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .collect(Collectors.toList()));
        return responsePadrao;
    }

    public ResponsePadrao getPautaById(Long id) {
        ResponsePadrao responsePadrao = new ResponsePadrao();
        responsePadrao.setObjeto(pautaRepository.findById(id)
                .map(p -> new PautaDTO(p.getId(),p.getTitulo(),p.getDescricao()))
                .orElseThrow(() ->
                new APIException(APIExceptionEnum.PautaNaoEncontrada)));
        return responsePadrao;
    }

    public ResponsePadrao inserirPauta(PautaRequest pauta){
        ResponsePadrao responsePadrao = new ResponsePadrao();
        if(pauta.getDescricao() == null ||pauta.getDescricao().isEmpty()){
            throw new APIException(APIExceptionEnum.DescricaoDeveSerPreenchido);
        }

        if(pauta.getTitulo() == null ||pauta.getTitulo().isEmpty()){
            throw new APIException(APIExceptionEnum.TituloDeveSerPreenchido);
        }

        PautaEntity pEntity = new PautaEntity();
        pEntity.setDescricao(pauta.getDescricao());
        pEntity.setTitulo(pauta.getTitulo());
        pautaRepository.save((pEntity));

        PautaDTO Dto = new PautaDTO(pEntity.getId(),pEntity.getTitulo(),pEntity.getDescricao());
        responsePadrao.setTexto("Pauta criada");
        responsePadrao.setObjeto(Dto);
        //responsePadrao.setObjeto(new PautaDTO(rep.save(pEntity)));

        return responsePadrao;

    }
}
