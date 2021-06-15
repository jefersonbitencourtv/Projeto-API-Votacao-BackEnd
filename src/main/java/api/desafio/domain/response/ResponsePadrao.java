package api.desafio.domain.response;
import api.desafio.domain.dto.AssociadoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePadrao {
    private String Texto;
    private Object Objeto;
    private List<Object> ListaObjeto;
}
