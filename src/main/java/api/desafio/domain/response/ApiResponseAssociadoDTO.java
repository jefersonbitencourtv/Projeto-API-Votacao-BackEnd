package api.desafio.domain.response;

import api.desafio.domain.dto.AssociadoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponseAssociadoDTO extends ApiResponse{
    private AssociadoDTO associado;
    private List<AssociadoDTO> listaAssociado;
}
