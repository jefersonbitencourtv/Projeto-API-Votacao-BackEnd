package api.desafio.domain.entities;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity(name = "Pauta")
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
