package api.desafio.domain.services;

import api.desafio.domain.entities.ResultadoEntity;
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
    @Autowired
    private PautaService pautaService;

    public Iterable<VotacaoEntity> getVotacao() {
        return rep.findAll();
    }

    public Optional<VotacaoEntity> getVotacaoById(Long id) {
        return rep.findById(id);
    }

    public VotacaoEntity save(VotacaoEntity votacao) throws Exception {
        //private LocalDateTime dataInicioVotacao = LocalDateTime.now();
        if(getVotacaoByIdPauta(votacao.getIdPauta()).isPresent()){
            throw new Exception("Já existe uma votação para a pauta");
        }
        if(!(pautaService.getPautaById(votacao.getIdPauta()).isPresent())){
            throw new Exception("A pauta não existe");
        }

        votacao.setDataAbertura(LocalDateTime.now());
        return rep.save(votacao);
    }

    private Optional<VotacaoEntity> getVotacaoByIdPauta(Long idPauta){
        return rep.findByIdPauta(idPauta);
    }

    public Long getDuracaoVotacao(Long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDuracaoVotacao();
    }

    public LocalDateTime getDataAbertura(Long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDataAbertura();
    }


}

