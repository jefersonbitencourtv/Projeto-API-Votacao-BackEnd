package api.desafio.api;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    @Autowired
    private PautaService service;

    @GetMapping
    public Iterable<PautaEntity> get() {
        return service.getPauta();
    }

    @GetMapping("/{id}")
    public Optional<PautaEntity> getId(@PathVariable Long id){
        return service.getPautaById(id);
    }

    @PostMapping
    public String postPauta(@RequestBody PautaEntity pauta){
        PautaEntity p = service.save(pauta);
        return "Pauta criada com sucesso, ID: " + p.getId();
    }
}
