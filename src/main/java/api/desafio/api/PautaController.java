package api.desafio.api;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    @Autowired
    private PautaService service;

    @GetMapping
    public ResponseEntity<ResponsePadrao> get() {

        return ResponseEntity.ok(service.getPauta());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePadrao> getId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getPautaById(id));

    }

    @PostMapping
    public ResponseEntity<ResponsePadrao> postPauta(@RequestBody PautaRequest pauta){

            return ResponseEntity.ok(service.save(pauta));

    }
}
