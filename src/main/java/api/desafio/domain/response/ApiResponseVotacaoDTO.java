package api.desafio.domain.response;

import api.desafio.domain.dto.VotacaoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Resposta para a solicitação da votação")
public class ApiResponseVotacaoDTO extends ApiResponse{
    @ApiModelProperty(value="Votação específico")
    private VotacaoDTO votacao;
    @ApiModelProperty(value="Todas votações")
    private List<VotacaoDTO> listaVotacao;
}
