package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautaDTO {
    private long id;
    private String titulo;
    private String descricao;

    public PautaDTO(PautaEntity p){
        this.id =  p.getId();
        this.titulo = p.getTitulo();
        this.descricao = p.getDescricao();
    }

    public PautaEntity PautaEntity(){
        return new PautaEntity(id,titulo,descricao);
    }
}
