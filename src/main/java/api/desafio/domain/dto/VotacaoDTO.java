package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotacaoDTO {
    private long id;
    //private PautaEntity pauta;
    private long idPauta;
    private Long duracaoVotacao;
    private LocalDateTime dataAbertura;

    /*public VotacaoDTO(VotacaoEntity v){
        this.id = v.getId();
        //this.idPauta = v.getIdPauta();
        this.duracaoVotacao = v.getDuracaoVotacao();
        this.dataAbertura = v.getDataAbertura();
    }*/

    public VotacaoEntity VotacaoEntity(){
        PautaEntity pauta = new PautaEntity();
        pauta.setId(idPauta);
        return new VotacaoEntity(this.id, pauta,this.duracaoVotacao,this.dataAbertura);
    }
}
