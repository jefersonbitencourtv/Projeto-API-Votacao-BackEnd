package api.desafio.domain.dto;

import api.desafio.domain.entities.VotacaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotacaoDTO {
    private long id;
    private long idPauta;
    private Long duracaoVotacao;
    private LocalDateTime dataAbertura;

    public VotacaoDTO(VotacaoEntity v){
        this.id = v.getId();
        this.idPauta = v.getIdPauta();
        this.duracaoVotacao = v.getDuracaoVotacao();
        this.dataAbertura = v.getDataAbertura();
    }

    public VotacaoEntity VotacaoEntity(){
        return new VotacaoEntity(this.id, this.idPauta,this.duracaoVotacao,this.dataAbertura);
    }
}
