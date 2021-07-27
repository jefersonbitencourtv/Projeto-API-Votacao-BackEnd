package api.desafio.controller;


import api.desafio.domain.response.ApiResponseResultadoDTO;
import api.desafio.domain.services.ResultadoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/resultado")
@Api(description="Funcionalidades para resultados", tags = "Resultado controller")
public class ResultadoController {
    @Autowired
    private ResultadoService service;

    @ApiOperation(value="Mostra todos resultados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca por todos resultados")
    })
    @GetMapping
    public ResponseEntity<ApiResponseResultadoDTO> getResultado(){
        return ResponseEntity.ok(service.getResultado());
    }

    @ApiOperation(value="Mostra resultado buscando pelo id da votação")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Sucesso na busca do resultado pelo ID da votação"),
            @ApiResponse(code = 404, message = "Resultado não encontrado para o ID de votação informado")
    })
    @GetMapping("/votacao/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoByIdVotacao(@PathVariable @ApiParam("ID da votação") Long id){
        return ResponseEntity.ok(service.getResultadoByIdVotacao(id));
    }


    @ApiOperation(value="Mostra resultado buscando pelo id da pauta")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Sucesso na busca do resultado pelo ID da pauta"),
            @ApiResponse(code = 404, message = "Resultado não encontrado para o ID de pauta informado")
    })
    @GetMapping("/pauta/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoByIdPauta(@PathVariable @ApiParam("ID da pauta") Long id){
        return ResponseEntity.ok(service.getResultadoByIdPauta(id));
    }

    @ApiOperation(value="Mostra resultando buscando pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Sucesso na busca do resultado pelo ID"),
            @ApiResponse(code = 404, message = "Resultado não encontrado para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseResultadoDTO> getResultadoById(@PathVariable @ApiParam("ID do resultado") Long id){
        return ResponseEntity.ok(service.getResultadoById(id));
    }
}
