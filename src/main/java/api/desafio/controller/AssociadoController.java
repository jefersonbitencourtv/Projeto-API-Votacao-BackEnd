package api.desafio.controller;

import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ApiResponseAssociadoDTO;
import api.desafio.domain.services.AssociadoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @ApiOperation(value="Mostra todos os associados")
    @GetMapping
    public ResponseEntity<ApiResponseAssociadoDTO> get(){
        return ResponseEntity.ok(service.getAssociado());
    }

    @ApiOperation(value="Mostra associado buscando pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseAssociadoDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getAssociadoById(id));
    }
    @ApiOperation(value="Cria associado")
    @PostMapping
    public ResponseEntity<ApiResponseAssociadoDTO> postAssociado(@RequestBody AssociadoRequest associado){
        return ResponseEntity.created(null).body(service.inserirAssociado(associado));
    }
}
