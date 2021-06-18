package api.desafio.domain.response;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePadrao {
    private String Texto;
    private Object Objeto;
    private List<Object> ListaObjeto;
}
