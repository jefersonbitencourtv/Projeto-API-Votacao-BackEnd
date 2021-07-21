package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {
    private long id;
    private long idVotacaoEntity;
    private long idPautaEntity;
    private int qtdSim;
    private int qtdNao;

    /*public ResultadoDTO(ResultadoEntity r){
        this.id = r.getId();
        this.votacaoEntity = r.getVotacaoEntity();
        this.pautaEntity = r.getPautaEntity();
        this.qtdSim = r.getQtdSim();
        this.qtdNao = r.getQtdNao();
    }*/

    public ResultadoEntity ResultadoEntity(){
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        PautaEntity pautaEntity = new PautaEntity();
        votacaoEntity.setId(idVotacaoEntity);
        pautaEntity.setId(idPautaEntity);
        return new ResultadoEntity(getId(), votacaoEntity, pautaEntity, getQtdSim(), getQtdNao());
    }
}

