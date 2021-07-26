package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Dados da votação")
public class VotacaoDTO {
    @ApiModelProperty(value="ID da votação", required = true)
    private long id;
    //private PautaEntity pauta;
    @ApiModelProperty(value="ID da pauta", required = true)
    private long idPauta;
    @ApiModelProperty(value="Duração da votação", required = true)
    private Long duracaoVotacao;
    @ApiModelProperty(value="Data de abertura da votação", required = true)
    private LocalDateTime dataAbertura;

    /*public VotacaoDTO(VotacaoEntity v){
        this.id = v.getId();
        //this.idPauta = v.getIdPauta();
        this.duracaoVotacao = v.getDuracaoVotacao();
        this.dataAbertura = v.getDataAbertura();
    }*/

    public VotacaoEntity VotacaoEntity(){
        PautaEntity pauta = new PautaEntity();
        pauta.setId(idPauta);
        return new VotacaoEntity(this.id, pauta,this.duracaoVotacao,this.dataAbertura);
    }
}
