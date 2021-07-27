package api.desafio.domain.dto;

import api.desafio.domain.entities.AssociadoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ApiModel(description = "Dados do associado")
public class AssociadoDTO {
    @ApiModelProperty(value="CPF do associado", required = true)
    private String cpf;
    @ApiModelProperty(value = "ID do associado", required = true)
    private long id;

    public AssociadoDTO(AssociadoEntity a){
        this.cpf = a.getCpf();
        this.id = a.getId();

    }
    public AssociadoEntity associadoEntity(){
        return new AssociadoEntity(id, cpf);
    }

}
