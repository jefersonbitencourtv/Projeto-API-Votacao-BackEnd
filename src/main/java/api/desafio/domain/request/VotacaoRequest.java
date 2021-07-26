package api.desafio.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Dados necessarios para a criar a votação")
public class VotacaoRequest{
    @ApiModelProperty(value="Id da pauta, não pode ser vazio", required = true)
    private Long idPauta;
    @ApiModelProperty(value="Duração da votação, caso vazio irá ser usado valor default de 1 minuto", required = false)
    private Long duracaoVotacao;
}
