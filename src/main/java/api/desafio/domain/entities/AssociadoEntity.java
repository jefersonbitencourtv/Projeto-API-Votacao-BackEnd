package api.desafio.domain.entities;

import lombok.*;

import javax.persistence.*;
@Entity(name="associado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssociadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="cpf")
    private String cpf;

}
