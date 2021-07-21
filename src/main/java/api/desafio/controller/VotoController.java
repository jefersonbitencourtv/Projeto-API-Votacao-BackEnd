package api.desafio.controller;

import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ApiResponseVotoDTO;
import api.desafio.domain.services.VotoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
    @Autowired
    private VotoService service;

    @ApiOperation(value="Mostra todos votos")
    @GetMapping
    public ResponseEntity<ApiResponseVotoDTO> get() {

        return ResponseEntity.ok(service.getVoto());
    }

    @ApiOperation(value="Mostra voto buscando pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseVotoDTO> getId(@PathVariable Long id){

        return ResponseEntity.ok(service.getVotoById(id));
    }

    @ApiOperation(value="Cria voto")
    @PostMapping
    public ResponseEntity<ApiResponseVotoDTO> postVoto(@RequestBody VotarRequest voto) {
        return ResponseEntity.created(null).body(service.inserirVoto(voto));

    }
}
