package api.desafio.api;

import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @GetMapping
    public ResponseEntity<ResponsePadrao> get(){
        return ResponseEntity.ok(service.getAssociado());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePadrao> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getAssociadoById(id));

    }

    @PostMapping
    public ResponseEntity<ResponsePadrao> postAssociado(@RequestBody AssociadoRequest associado){
            return ResponseEntity.ok(service.save(associado));

    }
}
