package api.desafio.domain.response;

import api.desafio.domain.dto.AssociadoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Resposta para a solicitação do associado")
public class ApiResponseAssociadoDTO extends ApiResponse{
    @ApiModelProperty(value="Associado específico")
    private AssociadoDTO associado;
    @ApiModelProperty(value="Todos associados")
    private List<AssociadoDTO> listaAssociado;
}
