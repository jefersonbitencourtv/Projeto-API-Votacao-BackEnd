package api.desafio.domain.dto;

import api.desafio.domain.entities.PautaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
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
}
