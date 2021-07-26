package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Dados da pauta")
public class PautaDTO {
    @ApiModelProperty(value="ID da pauta", required = true)
    private long id;
    @ApiModelProperty(value="Titulo da pauta", required = true)
    private String titulo;
    @ApiModelProperty(value="Descrição da pauta", required = true)
    private String descricao;

    public PautaDTO(PautaEntity p){
        this.id =  p.getId();
        this.titulo = p.getTitulo();
        this.descricao = p.getDescricao();
    }

    public PautaEntity PautaEntity(){
        return new PautaEntity(id,titulo,descricao);
    }
}
