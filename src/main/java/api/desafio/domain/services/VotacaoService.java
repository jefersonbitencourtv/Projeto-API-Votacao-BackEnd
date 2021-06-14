package api.desafio.domain.services;

import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotacaoService {

    @Autowired
    private VotacaoRepository rep;
    @Autowired
    private PautaService pautaService;

    public List<VotacaoDTO> getVotacao() {

        return rep.findAll().stream().map(v -> new VotacaoDTO(v)).collect(Collectors.toList());
    }

    public VotacaoDTO getVotacaoById(long id) {
        return rep.findById(id).map(v -> new VotacaoDTO(v)).orElseThrow(()
                -> new ObjectNotFoundException("Votacao não encontrada"));
    }

    public VotacaoDTO save(VotacaoEntity votacao) throws Exception {
        //private LocalDateTime dataInicioVotacao = LocalDateTime.now();
        if(rep.findById(votacao.getId()).isPresent()){
            throw new IllegalArgumentException("Votacao com id existente");
        }
        if(getVotacaoByIdPauta(votacao.getIdPauta()).isPresent()){
            throw new Exception("Já existe uma votação para a pauta");
        }
        pautaService.getPautaById(votacao.getIdPauta());

        votacao.setDataAbertura(LocalDateTime.now());
        return new VotacaoDTO(rep.save(votacao));
    }

    private Optional<VotacaoEntity> getVotacaoByIdPauta(long idPauta){
        return rep.findByIdPauta(idPauta);
    }

    public Long getDuracaoVotacao(long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDuracaoVotacao();
    }

    public LocalDateTime getDataAbertura(long id) {
        Optional<VotacaoEntity> findById = rep.findById(id);
        return findById.get().getDataAbertura();
    }


}

