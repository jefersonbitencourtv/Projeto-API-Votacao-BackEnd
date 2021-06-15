package api.desafio.domain.services;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.CampoInvalidoException;
import api.desafio.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PautaService {
    @Autowired
    private PautaRepository rep;

    private ResponsePadrao responseP = new ResponsePadrao();

    public ResponsePadrao getPauta() {
        responseP.setListaObjeto(rep.findAll().stream().map(p -> new PautaDTO(p)).collect(Collectors.toList()));
        return responseP;
    }

    public ResponsePadrao getPautaById(Long id) {
        responseP.setObjeto(rep.findById(id).map(p -> new PautaDTO(p)).orElseThrow(() ->
                new ObjetoNaoEncontradoException("Pauta não encontrada")));
        return responseP;
    }

    public ResponsePadrao save(PautaRequest pauta){
        if(pauta.getDescricao() == null ||pauta.getDescricao().isEmpty()){
            throw new CampoInvalidoException("Campo DESCRICAO deve ser preenchido");
        }

        if(pauta.getTitulo() == null ||pauta.getTitulo().isEmpty()){
            throw new CampoInvalidoException("Campo TITULO deve ser preenchido");
        }

        PautaEntity pEntity = new PautaEntity();

        pEntity.setDescricao(pauta.getDescricao());
        pEntity.setTitulo(pauta.getTitulo());
        responseP.setTexto("Pauta criada");
        responseP.setObjeto(new PautaDTO(rep.save(pEntity)));

        return responseP;
    }
}
