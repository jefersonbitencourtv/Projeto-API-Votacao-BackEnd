package api.desafio.controller;

import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.domain.services.VotacaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votacao")
public class VotacaoController {
    @Autowired
    private VotacaoService service;

    @ApiOperation(value="Mostra todas votações")
    @GetMapping
    public ResponseEntity<ApiResponseVotacaoDTO> get() {

        return ResponseEntity.ok(service.getVotacao());
    }

    @ApiOperation(value="Mostra votação buscando pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseVotacaoDTO> getId(@PathVariable Long id){
        return ResponseEntity.ok(service.getVotacaoById(id));
    }

    @ApiOperation(value="Cria votação")
    @PostMapping
    public ResponseEntity<ApiResponseVotacaoDTO> postVotacao(@RequestBody VotacaoRequest votacao) {
        return ResponseEntity.created(null).body(service.inserirVotacao(votacao));
            //return ResponseEntity.ok(service.inserirVotacao(votacao));
    }

}
