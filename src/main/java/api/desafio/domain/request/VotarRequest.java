package api.desafio.domain.request;

import lombok.Data;

@Data
public class VotarRequest {

    private Long idAssociado;

    private Long idVotacao;

    private String voto;
}
