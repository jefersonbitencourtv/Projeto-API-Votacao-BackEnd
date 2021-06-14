package api.desafio.domain.services;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultadoService {
    @Autowired
    private ResultadoRepository rep;
    @Autowired
    private VotacaoService vtservice;

    public void updateVoto(String voto, long idVotacao){
        ResultadoDTO db;
        if(getResultadoByIdVotacao2(idVotacao).isPresent()){
            ResultadoDTO rE = getResultadoByIdVotacao(idVotacao);
            db = rE;
        }else {
            db = new ResultadoDTO();
            db.setIdVotacao(idVotacao);

            VotacaoDTO vEnt = vtservice.getVotacaoById(idVotacao);
            VotacaoEntity vEntity = vEnt.VotacaoEntity();

            db.setIdPauta(vEntity.getIdPauta());
        }

            //VotacaoEntity db = vt.get();
            if(voto.equals("SIM")) {
                int i = db.getQtdSim();
                db.setQtdSim(i+1);
            }
            if(voto.equals("NAO")) {
                int i = db.getQtdNao();
                db.setQtdNao(i+1);
            }
            //ResultadoEntity re = new ResultadoEntity();

            //re.setIdPauta(db.getIdPauta());
            //re.setIdVotacao(db.getIdVotacao());
            //re.setQtdNao(db.getQtdNao());
            //re.setQtdSim(db.getQtdSim());

            rep.save(db.ResultadoEntity());
            //rep.save(re);
        }

    public ResultadoDTO getResultadoByIdVotacao(Long idVotacao) {
        return rep.findByIdVotacao(idVotacao).map(r->new ResultadoDTO(r)).orElseThrow(()
                ->new ObjectNotFoundException("Não existe resultado para essa votação"));
    }

    public Optional<ResultadoEntity> getResultadoByIdVotacao2(long idVotacao){
        return rep.findByIdVotacao(idVotacao);
    }

    public ResultadoDTO getResultadoByIdPauta(Long idPauta) {
        return rep.findByIdPauta(idPauta).map(r -> new ResultadoDTO(r)).orElseThrow(()
        -> new ObjectNotFoundException("Não existe resultado para essa pauta"));
    }

    public List<ResultadoDTO> getResultado() {
        return rep.findAll().stream().map(r->new ResultadoDTO(r)).collect(Collectors.toList());
    }

    public ResultadoDTO getResultadoById(Long id){

        return rep.findById(id).map(v->new ResultadoDTO(v)).orElseThrow(()
                -> new ObjectNotFoundException("Não existe resultado com esse id"));
    }
}
