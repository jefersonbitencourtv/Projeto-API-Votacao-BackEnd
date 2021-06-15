package api.desafio.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
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

    public VotarEntity(long id, long idAssociado, long idVotacao, String voto) {
    }
}
