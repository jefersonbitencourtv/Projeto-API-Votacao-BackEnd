package api.desafio.domain.response;

import api.desafio.domain.dto.PautaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(description = "Resposta para a solicitação da pauta")
public class ApiResponsePautaDTO extends ApiResponse{
    @ApiModelProperty(value="Pauta específica")
    private PautaDTO pauta;
    @ApiModelProperty(value="Todas pautas")
    private List<PautaDTO> listaPauta;
}
