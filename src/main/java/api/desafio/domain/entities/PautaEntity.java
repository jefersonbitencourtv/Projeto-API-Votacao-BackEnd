package api.desafio.domain.entities;

import lombok.*;

import javax.persistence.*;
@Entity(name = "Pauta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PautaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="Titulo")
    private String titulo;

    @Column(name="Descricao")
    private String descricao;

}
