package api.desafio.controller;

import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.domain.services.PautaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    @Autowired
    private PautaService service;

    @ApiOperation(value="Mostra todas pautas")
    @GetMapping
    public ResponseEntity<ApiResponsePautaDTO> get() {

        return ResponseEntity.ok(service.getPauta());
    }
    @ApiOperation(value="Mostra pauta buscando pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponsePautaDTO> getId(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getPautaById(id));

    }
    @ApiOperation(value="Cria pauta")
    @PostMapping
    public ResponseEntity<ApiResponsePautaDTO> postPauta(@RequestBody PautaRequest pauta){
        return ResponseEntity.created(null).body(service.inserirPauta(pauta));
        //return ResponseEntity.ok(service.inserirPauta(pauta));

    }
}
