package api.desafio.domain.response;

import api.desafio.domain.dto.PautaDTO;
import lombok.Data;

import java.util.List;
@Data
public class ApiResponsePautaDTO extends ApiResponse{
    private PautaDTO pauta;
    private List<PautaDTO> listaPauta;
}
