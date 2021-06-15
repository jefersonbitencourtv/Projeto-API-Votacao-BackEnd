package api.desafio.domain.services;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoService {
    @Autowired
    private ResultadoRepository rep;
    @Autowired
    private VotacaoService vtservice;

    private ResponsePadrao responseP = new ResponsePadrao();

    public void updateVoto(String voto, long idVotacao){
        ResultadoDTO db;
        if(getResultadoByIdVotacao2(idVotacao).isPresent()){
            Optional<ResultadoDTO> rE = getResultadoByIdVotacao2(idVotacao);
            db = rE.get();
        }else {
            db = new ResultadoDTO();
            db.setIdVotacao(idVotacao);

            VotacaoDTO vEnt = vtservice.getVotacaoById2(idVotacao);
            VotacaoEntity vEntity = vEnt.VotacaoEntity();

            db.setIdPauta(vEntity.getIdPauta());
        }


            if(voto.equals("SIM")) {
                int i = db.getQtdSim();
                db.setQtdSim(i+1);
            }
            if(voto.equals("NAO")) {
                int i = db.getQtdNao();
                db.setQtdNao(i+1);
            }


            rep.save(db.ResultadoEntity());

        }

    public ResponsePadrao getResultadoByIdVotacao(Long idVotacao) {
        responseP.setObjeto(rep.findByIdVotacao(idVotacao).map(r->new ResultadoDTO(r)).orElseThrow(()
                ->new ObjetoNaoEncontradoException("Não existe resultado para essa votação")));
        return responseP;
    }

    public Optional<ResultadoDTO> getResultadoByIdVotacao2(long idVotacao){
        return rep.findByIdVotacao(idVotacao).map(r->new ResultadoDTO(r));
    }

    public ResponsePadrao getResultadoByIdPauta(Long idPauta) {
        responseP.setObjeto(rep.findByIdPauta(idPauta).map(r -> new ResultadoDTO(r)).orElseThrow(()
        -> new ObjetoNaoEncontradoException("Não existe resultado para essa pauta")));
        return responseP;
    }

    public ResponsePadrao getResultado() {
        responseP.setListaObjeto(
                rep.findAll().stream().map(r->new ResultadoDTO(r)).collect(Collectors.toList()));
        return responseP;
    }

    public ResponsePadrao getResultadoById(Long id){
        responseP.setObjeto(rep.findById(id).map(v->new ResultadoDTO(v)).orElseThrow(()
                -> new ObjetoNaoEncontradoException("Não existe resultado com esse id")));
        return responseP;
    }
}
