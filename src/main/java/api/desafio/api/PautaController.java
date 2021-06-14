package api.desafio.api;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.services.PautaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    @Autowired
    private PautaService service;

    @GetMapping
    public ResponseEntity<List<PautaDTO>> get() {
        return ResponseEntity.ok(service.getPauta());
    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable("id") Long id){
        PautaDTO pDTO = service.getPautaById(id);
        return ResponseEntity.ok(pDTO);

    }

    @PostMapping
    public ResponseEntity postPauta(@RequestBody PautaEntity pauta){
            PautaDTO pDTO = service.save(pauta);
            URI location = getUri(pDTO.getId());
            return ResponseEntity.created(location).build();

    }

    private URI getUri(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
                .buildAndExpand(id).toUri();
    }
}
