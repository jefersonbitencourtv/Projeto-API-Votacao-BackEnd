package api.desafio.domain.response;

import api.desafio.domain.dto.VotacaoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponseVotacaoDTO extends ApiResponse{
    private VotacaoDTO votacao;
    private List<VotacaoDTO> listaVotacao;
}
