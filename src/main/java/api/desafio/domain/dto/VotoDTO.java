package api.desafio.domain.dto;

import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VotoDTO {
    private long id;
    private long idAssociado;
    private long idVotacao;
    private String voto;

    /*public VotoDTO(VotoEntity v){
        this.id = v.getId();
        this.associadoEntity = v.getAssociadoEntity();
        this.votacaoEntity = v.getVotacaoEntity();
        this.voto = v.getVoto();
    }

    public VotoEntity VotarEntity(){
        AssociadoEntity associadoEntity = new AssociadoEntity();
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        associadoEntity.setId(idAssociado);
        votacaoEntity.setId(idVotacao);
        return new VotoEntity(this.id, associadoEntity,votacaoEntity,this.voto);
    }*/
}