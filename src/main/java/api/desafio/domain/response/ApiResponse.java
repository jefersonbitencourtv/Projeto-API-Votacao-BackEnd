package api.desafio.domain.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "Resposta para a solicitação")
public class ApiResponse {
    @ApiModelProperty(value="Texto informando sobre a solicitação", required = true)
    private String mensagem;
    @ApiModelProperty(value="HttpStatus da solicitação", required = true)
    private HttpStatus status;
    @ApiModelProperty(value="Horario da solicitação", required = true)
    private LocalDateTime horario = LocalDateTime.now();

    //private Object Objeto;
    //private List<Object> ListaObjeto;
}
