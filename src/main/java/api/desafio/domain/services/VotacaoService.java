package api.desafio.domain.services;

import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.exception.CampoInvalidoException;
import api.desafio.exception.JaExisteVotacaoParaAPautaException;
import api.desafio.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotacaoService {

    @Autowired
    private VotacaoRepository rep;
    @Autowired
    private PautaService pautaService;

    private ResponsePadrao responseP = new ResponsePadrao();

    public ResponsePadrao getVotacao() {
        responseP.setListaObjeto(rep.findAll().stream().map(v -> new VotacaoDTO(v)).collect(Collectors.toList()));
        return responseP;
    }

    public VotacaoDTO getVotacaoById2(long id) {
        return rep.findById(id).map(v -> new VotacaoDTO(v)).orElseThrow(()
                -> new ObjetoNaoEncontradoException("Votacao não encontrada"));
    }

    public ResponsePadrao getVotacaoById(long id) {
        responseP.setObjeto(rep.findById(id).map(v -> new VotacaoDTO(v)).orElseThrow(()
                -> new ObjetoNaoEncontradoException("Votacao não encontrada")));
        return responseP;
    }

    public ResponsePadrao save(VotacaoRequest votacao){
        if(votacao.getIdPauta() == null){
            throw new CampoInvalidoException("IdPauta deve ser forneceido");
        }
        if(getVotacaoByIdPauta(votacao.getIdPauta()).isPresent()){
            throw new JaExisteVotacaoParaAPautaException("Já existe uma votação para a pauta");
        }

        pautaService.getPautaById(votacao.getIdPauta());
        VotacaoEntity ve = new VotacaoEntity();
        ve.setDataAbertura(LocalDateTime.now());
        ve.setDuracaoVotacao(votacao.getDuracaoVotacao());
        ve.setIdPauta(votacao.getIdPauta());

        responseP.setObjeto(new VotacaoDTO(rep.save(ve)));
        responseP.setTexto("Votacao criada com sucesso");
        return responseP;
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

