package api.desafio.domain.response;

import api.desafio.domain.dto.VotoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponseVotoDTO extends ApiResponse{
    private VotoDTO voto;
    private List<VotoDTO> listaVoto;
}
