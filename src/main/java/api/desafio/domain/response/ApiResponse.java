package api.desafio.domain.response;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiResponse {
    private String mensagem;
    private HttpStatus status;
    private LocalDateTime horario = LocalDateTime.now();

    //private Object Objeto;
    //private List<Object> ListaObjeto;
}
