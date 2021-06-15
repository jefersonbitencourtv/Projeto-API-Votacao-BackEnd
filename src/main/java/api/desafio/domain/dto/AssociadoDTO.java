package api.desafio.domain.dto;

import api.desafio.domain.entities.AssociadoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssociadoDTO {
    private String cpf;
    private long id;

    public AssociadoDTO(AssociadoEntity a){
        this.cpf = a.getCpf();
        this.id = a.getId();

    }

}
