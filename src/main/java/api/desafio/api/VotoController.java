package api.desafio.api;

import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
    @Autowired
    private VotoService service;

    @GetMapping
    public Iterable<VotarEntity> get() {
        return service.getVoto();
    }

    @GetMapping("/{id}")
    public Optional<VotarEntity> getId(@PathVariable Long id){
        return service.getVotoById(id);
    }

    @PostMapping
    public String postVotacao(@RequestBody VotarEntity voto) throws Exception {
        VotarEntity v = service.save(voto);
        return "Voto criada com sucesso, ID: " + v.getId();
    }
}
