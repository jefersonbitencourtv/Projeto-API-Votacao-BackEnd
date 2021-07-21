package api.desafio.domain.dto;

import api.desafio.domain.entities.AssociadoEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssociadoDTO {
    private String cpf;
    private long id;

    public AssociadoDTO(AssociadoEntity a){
        this.cpf = a.getCpf();
        this.id = a.getId();

    }
    public AssociadoEntity associadoEntity(){
        return new AssociadoEntity(id, cpf);
    }

}
