package api.desafio.controller;


import api.desafio.domain.response.ApiResponseResultadoDTO;
import api.desafio.domain.services.ResultadoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/resultado")
public class ResultadoController {
    @Autowired
    private ResultadoService service;

    @ApiOperation(value="Mostra todos resultados")
    @GetMapping
    public ResponseEntity<ApiResponseResultadoDTO> getResultado(){
        return ResponseEntity.ok(service.getResultado());
    }

    @ApiOperation(value="Mostra resultado buscando pelo id da votação")
    @GetMapping("/votacao/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoByIdVotacao(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoByIdVotacao(id));
    }
    @ApiOperation(value="Mostra resultado buscando pelo id da pauta")
    @GetMapping("/pauta/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoByIdPauta(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoByIdPauta(id));
    }

    @ApiOperation(value="Mostra resultando buscando pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoById(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoById(id));
    }
}
