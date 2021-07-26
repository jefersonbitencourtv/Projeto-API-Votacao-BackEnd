package api.desafio.domain.dto;

import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Dados do voto")
public class VotoDTO {
    @ApiModelProperty(value="ID do voto", required = true)
    private long id;
    @ApiModelProperty(value="ID do associado", required = true)
    private long idAssociado;
    @ApiModelProperty(value="ID da votação", required = true)
    private long idVotacao;
    @ApiModelProperty(value="Voto escolhido(sim ou não)", required = true)
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