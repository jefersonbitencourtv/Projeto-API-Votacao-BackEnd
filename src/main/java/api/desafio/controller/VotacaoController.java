package api.desafio.controller;

import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.domain.services.VotacaoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votacao")
@Api(description="Funcionalidades para votacao", tags = "Votacao controller")
public class VotacaoController {
    @Autowired
    private VotacaoService service;

    @ApiOperation(value="Mostra todas votações")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca por todas votacoes")
    })
    @GetMapping
    public ResponseEntity<ApiResponseVotacaoDTO> get() {
        return ResponseEntity.ok(service.getVotacao());
    }

    @ApiOperation(value="Mostra votação buscando pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca da votação por ID"),
            @ApiResponse(code = 404, message = "Votação não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseVotacaoDTO> getId(@PathVariable @ApiParam("ID da votação") Long id){
        return ResponseEntity.ok(service.getVotacaoById(id));
    }

    @ApiOperation(value="Cria votação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao criar votação"),
            @ApiResponse(code = 400, message = "Campo invalido"),
            @ApiResponse(code = 404, message = "Não foi possivel encontrar um dos dados informados no banco de dados"),
            @ApiResponse(code = 409, message = "Conflito com dados existentes"),
            @ApiResponse(code = 422, message = "O servidor entende a requisação e a sintaxe esta correta, mas não foi possivel processar as instruções")

    })
    @PostMapping
    public ResponseEntity<ApiResponseVotacaoDTO> postVotacao(@RequestBody @ApiParam("Dados da votação") VotacaoRequest votacao) {
        return ResponseEntity.created(null).body(service.inserirVotacao(votacao));
            //return ResponseEntity.ok(service.inserirVotacao(votacao));
    }

}
