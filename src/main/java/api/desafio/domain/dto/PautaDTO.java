package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

//nao precisa por essas anotacoes pq elas já estão incluídas no @Data
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(description = "Dados da pauta")
public class PautaDTO {
    @ApiModelProperty(value="ID da pauta", required = true)
    private long id;
    @ApiModelProperty(value="Titulo da pauta", required = true)
    private String titulo;
    @ApiModelProperty(value="Descrição da pauta", required = true)
    private String descricao;

    //Sugestao de nome para o parâmetro, é super importante escolher bons nome
    //Tu colocou @Allargdsconstructor entao supostamente nao precisaria colocar esse construtor aqui com parametros, eu nao entendi na vdd pq esse construtor
    //vm mete uma call dai tu me explica
    public PautaDTO(PautaEntity pautaEntity){
        this.id =  pautaEntity.getId();
        this.titulo = pautaEntity.getTitulo();
        this.descricao = pautaEntity.getDescricao();
    }

    public PautaEntity PautaEntity(){
        return new PautaEntity(id,titulo,descricao);
    }
}
