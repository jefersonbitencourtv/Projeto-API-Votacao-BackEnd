package api.desafio.domain.services;

import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotacaoService {
    @Autowired
    private VotacaoRepository rep;
    public Iterable<VotacaoEntity> getVotacao(){
        return rep.findAll();
    }

    public Optional<VotacaoEntity> getVotacaoById(Long id){
        return rep.findById(id);
    }

    public VotacaoEntity save(VotacaoEntity votacao){
        return rep.save(votacao);
    }




}
