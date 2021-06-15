package api.desafio.domain.dto;

import api.desafio.domain.entities.ResultadoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoDTO {
    private long id;
    private long idVotacao;
    private long idPauta;
    private int qtdSim;
    private int qtdNao;

    public ResultadoDTO(ResultadoEntity r){
        this.id = r.getId();
        this.idVotacao = r.getIdVotacao();
        this.idPauta = r.getIdPauta();
        this.qtdSim = r.getQtdSim();
        this.qtdNao = r.getQtdNao();
    }

    public ResultadoEntity ResultadoEntity(){
        return new ResultadoEntity(id, idVotacao, idPauta, qtdSim, qtdNao);
    }
}

