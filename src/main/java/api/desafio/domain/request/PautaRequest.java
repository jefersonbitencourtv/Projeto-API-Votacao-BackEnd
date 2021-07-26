package api.desafio.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Dados necessarios para criar a pauta")
public class PautaRequest {
    @ApiModelProperty(value="Titulo, não pode ser vazio", required = true)
    private String titulo;
    @ApiModelProperty(value="Descrição, não pode ser vazio", required = true)
    private String descricao;

}
