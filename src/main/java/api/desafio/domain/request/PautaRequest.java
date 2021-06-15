package api.desafio.domain.request;

import api.desafio.domain.entities.PautaEntity;
import lombok.Data;

@Data
public class PautaRequest {
    private String titulo;
    private String descricao;

}
