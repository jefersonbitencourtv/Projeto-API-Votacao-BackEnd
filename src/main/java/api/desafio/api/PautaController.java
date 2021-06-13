package api.desafio.api;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.services.PautaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    @Autowired
    private PautaService service;

    @GetMapping
    public ResponseEntity<List<PautaDTO>> get() {
        return ResponseEntity.ok(service.getPauta());
    }

    @GetMapping("/{id}")
    public ResponseEntity getId(@PathVariable Long id){
        Optional<PautaDTO> pDTO = service.getPautaById(id);
        return pDTO.map(p-> ResponseEntity.ok(p)).orElse(ResponseEntity.notFound().build());
        /*return pDTO.isPresent() ?
                ResponseEntity.ok(pDTO.get()) :
                ResponseEntity.notFound().build();*/
        /*if(pDTO.isPresent()){
            return ResponseEntity.ok(pDTO.get());
        }else{
            return ResponseEntity.notFound().build();
        }*/
    }

    @PostMapping
    public ResponseEntity postPauta(@RequestBody PautaEntity pauta){
        try{
            PautaDTO pDTO = service.save(pauta);
            URI location = getUri(pDTO.getId());
            return ResponseEntity.created(location).build();
        }catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    private URI getUri(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
                .buildAndExpand(id).toUri();
    }
}
