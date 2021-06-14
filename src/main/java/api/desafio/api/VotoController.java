package api.desafio.api;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.services.VotoService;
import api.desafio.exception.ObjectNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
    @Autowired
    private VotoService service;

    @GetMapping
    public ResponseEntity<List<VotarDTO>> get() {
        return ResponseEntity.ok(service.getVoto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotarDTO> getId(@PathVariable Long id){

        return ResponseEntity.ok(service.getVotoById(id));
    }

    @PostMapping
    public ResponseEntity postVoto(@RequestBody VotarEntity voto) throws Exception {
        service.save(voto);
        return ResponseEntity.created(null).build();
    }
}
