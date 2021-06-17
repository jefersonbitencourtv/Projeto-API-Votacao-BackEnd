package api.desafio.api;

import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
    @Autowired
    private VotoService service;

    @GetMapping
    public ResponseEntity<ResponsePadrao> get() {

        return ResponseEntity.ok(service.getVoto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePadrao> getId(@PathVariable Long id){

        return ResponseEntity.ok(service.getVotoById(id));
    }

    @PostMapping
    public ResponseEntity<ResponsePadrao> postVoto(@RequestBody VotarRequest voto) {
        return ResponseEntity.ok(service.inserirVoto(voto));

    }
}
