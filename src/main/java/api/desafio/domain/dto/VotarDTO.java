package api.desafio.domain.dto;

import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VotarDTO {
    private long id;
    private long idAssociado;
    private long idVotacao;
    private String voto;

    public VotarDTO(VotarEntity v){
        this.id = v.getId();
        this.idAssociado = v.getIdAssociado();
        this.idVotacao = v.getIdVotacao();
        this.voto = v.getVoto();
    }

    public VotarEntity VotarEntity(){
        return new VotarEntity(this.id, this.idAssociado,this.idVotacao,this.voto);
    }
}