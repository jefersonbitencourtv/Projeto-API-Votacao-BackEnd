package api.desafio.domain.entities;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity(name="votar")
public class VotarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @Column(name="Id_Associado")
    private long idAssociado;

    @Column(name = "Id_Votacao")
    private long idVotacao;

    @Column(name = "Voto")
    private String voto;

}
