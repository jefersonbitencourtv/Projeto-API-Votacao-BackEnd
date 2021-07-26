package api.desafio.controller;

import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.domain.services.PautaService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pauta")
@Api(description="Funcionalidades para pautas", tags = "Pauta controller")
public class PautaController {
    @Autowired
    private PautaService service;

    @ApiOperation(value="Mostra todas pautas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca por todas pautas")
    })
    @GetMapping
    public ResponseEntity<ApiResponsePautaDTO> get() {
        return ResponseEntity.ok(service.getPauta());
    }

    @ApiOperation(value="Mostra pauta buscando pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca da pauta por ID"),
            @ApiResponse(code = 404, message = "Pauta não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponsePautaDTO> getId(@PathVariable("id") @ApiParam("ID da pauta") Long id){
        return ResponseEntity.ok(service.getPautaById(id));
    }

    @ApiOperation(value="Cria pauta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao criar pauta"),
            @ApiResponse(code = 400, message = "Dados informados invalidos"),
    })
    @PostMapping
    public ResponseEntity<ApiResponsePautaDTO> postPauta(@RequestBody @ApiParam("Dados da pauta") PautaRequest pauta){
        return ResponseEntity.created(null).body(service.inserirPauta(pauta));
    }
}
