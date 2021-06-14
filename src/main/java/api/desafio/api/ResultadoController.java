package api.desafio.api;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.services.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/resultado")
public class ResultadoController {
    @Autowired
    private ResultadoService service;

    @GetMapping
    public ResponseEntity<List<ResultadoDTO>> getResultado(){
        return ResponseEntity.ok(service.getResultado());
    }

    @GetMapping("/votacao/{id}")
    public ResponseEntity<ResultadoDTO> getResultadoByIdVotacao(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoByIdVotacao(id));
    }
    @GetMapping("/pauta/{id}")
    public ResponseEntity<ResultadoDTO> getResultadoByIdPauta(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoByIdPauta(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoDTO> getResultadoById(@PathVariable Long id){
        return ResponseEntity.ok(service.getResultadoById(id));
    }
}
