package api.desafio.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="associado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="cpf")
    private long cpf;
}
