package api.desafio.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Dados necessarios para criar o associado")
public class AssociadoRequest {
    @ApiModelProperty(value="CPF, não pode ser vazio", required = true)
    private String cpf;
}
