package api.desafio.domain.response;

import api.desafio.domain.dto.ResultadoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponseResultadoDTO extends ApiResponse {
    private ResultadoDTO resultado;
    private List<ResultadoDTO> listaResultado;
}
