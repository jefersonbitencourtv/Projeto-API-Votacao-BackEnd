package api.desafio.domain.response;

import api.desafio.domain.dto.VotoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Resposta para a solicitação de voto")
public class ApiResponseVotoDTO extends ApiResponse{
    @ApiModelProperty(value="Voto específico")
    private VotoDTO voto;
    @ApiModelProperty(value="Todos votos")
    private List<VotoDTO> listaVoto;
}
