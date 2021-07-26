package api.desafio.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Dados necessarios para criar o voto")
public class VotarRequest {
    @ApiModelProperty(value="ID do associado, não pode ser vazio", required = true)
    private Long idAssociado;
    @ApiModelProperty(value="ID da votação, não pode ser vazio", required = true)
    private Long idVotacao;
    @ApiModelProperty(value="Escolha do voto(sim ou não)", required = true)
    private String voto;
}
