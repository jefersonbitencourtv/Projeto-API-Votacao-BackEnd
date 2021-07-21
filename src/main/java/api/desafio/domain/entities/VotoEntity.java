package api.desafio.domain.entities;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="votar")
public class VotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @OneToOne
    @JoinColumn(name="Id_Associado", referencedColumnName = "Id")
    private AssociadoEntity associadoEntity;

    @OneToOne
    @JoinColumn(name = "Id_Votacao", referencedColumnName = "Id")
    private VotacaoEntity votacaoEntity;

    @Column(name = "Voto")
    private String voto;

}
