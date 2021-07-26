package api.desafio.controller;

import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ApiResponseVotoDTO;
import api.desafio.domain.services.VotoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voto")
@Api(description="Funcionalidades para votos", tags = "Voto controller")
public class VotoController {
    @Autowired
    private VotoService service;

    @ApiOperation(value="Mostra todos votos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca por todos votos")
    })
    @GetMapping
    public ResponseEntity<ApiResponseVotoDTO> get() {

        return ResponseEntity.ok(service.getVoto());
    }

    @ApiOperation(value="Mostra voto buscando pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca do voto por ID"),
            @ApiResponse(code = 404, message = "Voto não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseVotoDTO> getId(@PathVariable @ApiParam("ID do voto") Long id){

        return ResponseEntity.ok(service.getVotoById(id));
    }

    @ApiOperation(value="Cria voto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao criar voto"),
            @ApiResponse(code = 400, message = "Campo invalido"),
            @ApiResponse(code = 404, message = "Não foi possivel encontrar um dos dados informados no banco de dados"),
            @ApiResponse(code = 409, message = "Conflito com dados existentes")

    })
    @PostMapping
    public ResponseEntity<ApiResponseVotoDTO> postVoto(@RequestBody @ApiParam("Dados do voto") VotarRequest voto) {
        return ResponseEntity.created(null).body(service.inserirVoto(voto));

    }
}
