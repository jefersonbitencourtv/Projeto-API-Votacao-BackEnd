package api.desafio.domain.services;

import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class VotacaoService {

    @Autowired
    private VotacaoRepository rep;

    public Iterable<VotacaoEntity> getVotacao() {
        return rep.findAll();
    }

    public Optional<VotacaoEntity> getVotacaoById(Long id) {
        return rep.findById(id);
    }

    public VotacaoEntity save(VotacaoEntity votacao) {
        //private LocalDateTime dataInicioVotacao = LocalDateTime.now();
        votacao.setDataAbertura(LocalDateTime.now());
        return rep.save(votacao);
    }

    public Long getDuracaoVotacao(Long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDuracaoVotacao();
    }

    public LocalDateTime getDataAbertura(Long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDataAbertura();
    }

    public void updateVoto(String voto, Long id){
        Optional<VotacaoEntity> vt = getVotacaoById(id);
        VotacaoEntity db = vt.get();

        if(vt.isPresent()){
            //VotacaoEntity db = vt.get();
            if(voto.equals("SIM")) {
                int i = db.getQuantidadeSim();
                db.setQuantidadeSim(i+1);
            }
            if(voto.equals("NAO")) {
                int i = db.getQuantidadeNao();
                db.setQuantidadeNao(i+1);
            }
            rep.save(db);
        }
    }
}

