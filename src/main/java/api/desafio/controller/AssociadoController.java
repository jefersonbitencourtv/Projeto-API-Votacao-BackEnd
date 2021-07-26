package api.desafio.controller;

import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ApiResponseAssociadoDTO;
import api.desafio.domain.services.AssociadoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/associado")
@Api(description="Funcionalidades para associados", tags = "Associado controller")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @ApiOperation(value="Mostra todos os associados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso na busca por todos associados")
    })
    @GetMapping
    public ResponseEntity<ApiResponseAssociadoDTO> get(){
        return ResponseEntity.ok(service.getAssociado());
    }

    @ApiOperation(value="Mostra associado buscando pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Sucesso na busca do associado por id"),
            @ApiResponse(code = 404, message = "Associado não encontrado para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseAssociadoDTO> getById(@PathVariable @ApiParam("ID do associado") Long id){
        return ResponseEntity.ok(service.getAssociadoById(id));
    }

    @ApiOperation(value="Cria associado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Sucesso ao criar associado"),
            @ApiResponse(code = 400, message = "Dados informados invalidos"),
            @ApiResponse(code = 409, message = "Conflito com os dados no sistema"),
            @ApiResponse(code = 422, message = "Erro interno ao validar cpf")
    })
    @PostMapping
    public ResponseEntity<ApiResponseAssociadoDTO> postAssociado(@RequestBody @ApiParam("Dados do associado") AssociadoRequest associado) {
        return ResponseEntity.created(null).body(service.inserirAssociado(associado));
    }
}
