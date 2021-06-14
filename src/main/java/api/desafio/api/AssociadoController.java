package api.desafio.api;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.AssociadoResponse;
import api.desafio.domain.services.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.SequenceGenerator;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {
    @Autowired
    private AssociadoService service;

    @GetMapping
    public ResponseEntity<List<AssociadoDTO>> get(){
        return ResponseEntity.ok(service.getAssociado());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Optional<AssociadoDTO> aDTO = service.getAssociadoById(id);
        return aDTO.map(a -> ResponseEntity.ok(a)).orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<AssociadoResponse> postAssociado(@RequestBody AssociadoRequest associado){
        try{
            //AssociadoDTO aDTO = service.save(associado);
            //URI location = getUri(aDTO.getId());
            //return ResponseEntity.created(location).build();
            return ResponseEntity.ok(service.save(associado));
        }catch(Exception ex){
            System.out.println("ERROR :  " + ex);
            return ResponseEntity.badRequest().build();
        }
    }


    private URI getUri(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }


}
