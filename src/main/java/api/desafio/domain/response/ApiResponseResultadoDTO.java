package api.desafio.domain.response;

import api.desafio.domain.dto.ResultadoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Resposta para a solicitação do resultado")
public class ApiResponseResultadoDTO extends ApiResponse {
    @ApiModelProperty(value="Resultado específico")
    private ResultadoDTO resultado;
    @ApiModelProperty(value="Todos resultados")
    private List<ResultadoDTO> listaResultado;
}
