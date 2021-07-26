package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Dados do resultado")
public class ResultadoDTO {
    @ApiModelProperty(value="ID do resultado", required = true)
    private long id;
    @ApiModelProperty(value="ID da votacao", required = true)
    private long idVotacao;
    @ApiModelProperty(value="ID da pauta", required = true)
    private long idPauta;
    @ApiModelProperty(value="Quantidade de votos sim", required = true)
    private int qtdSim;
    @ApiModelProperty(value="Quantidade de votos não", required = true)
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
        votacaoEntity.setId(idVotacao);
        pautaEntity.setId(idPauta);
        return new ResultadoEntity(getId(), votacaoEntity, pautaEntity, getQtdSim(), getQtdNao());
    }
}

