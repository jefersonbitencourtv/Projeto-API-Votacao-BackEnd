package api.desafio.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Dados necessarios para buscar o resultado")
public class ResultadoRequest {
    @ApiModelProperty(value="Id da votação", required = false)
    private long idVotacao;
    @ApiModelProperty(value="Id da pauta", required = false)
    private long idPauta;
}
