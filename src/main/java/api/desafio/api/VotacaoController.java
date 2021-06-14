package api.desafio.api;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.services.PautaService;
import api.desafio.domain.services.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1/votacao")
public class VotacaoController {
    @Autowired
    private VotacaoService service;

    @GetMapping
    public ResponseEntity<List<VotacaoDTO>> get() {
        return ResponseEntity.ok(service.getVotacao());
    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable Long id){
        return ResponseEntity.ok(service.getVotacaoById(id));
    }

    @PostMapping
    public ResponseEntity postVotacao(@RequestBody VotacaoEntity votacao) throws Exception {
        if(votacao.getDuracaoVotacao()==null) {
            votacao.setDuracaoVotacao(1L);
        }
        VotacaoDTO vDTO = service.save(votacao);
        return ResponseEntity.created(null).build();
    }
}
