package api.desafio.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class ResultadoRequest {
    private long idVotacao;

    private long idPauta;
}
