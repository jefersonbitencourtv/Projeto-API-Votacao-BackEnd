package api.desafio.api;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.services.PautaService;
import api.desafio.domain.services.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/api/v1/votacao")
public class VotacaoController {
    @Autowired
    private VotacaoService service;

    @GetMapping
    public Iterable<VotacaoEntity> get() {
        return service.getVotacao();
    }

    @GetMapping("/{id}")
    public Optional<VotacaoEntity> getId(@PathVariable Long id){
        return service.getVotacaoById(id);
    }

    @PostMapping
    public String postVotacao(@RequestBody VotacaoEntity votacao){
        if(votacao.getDuracaoVotacao()==null) {
            votacao.setDuracaoVotacao(1L);
        }
        VotacaoEntity v = service.save(votacao);
        return "Votacao criada com sucesso, ID: " + v.getId();
    }
}
