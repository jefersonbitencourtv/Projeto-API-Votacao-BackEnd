package api.desafio.domain.entities;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Resultado")
public class ResultadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @OneToOne
    @JoinColumn(name="id_votacao", referencedColumnName = "id")
    private VotacaoEntity votacaoEntity;

    @OneToOne
    @JoinColumn(name="id_pauta", referencedColumnName = "Id")
    private PautaEntity pautaEntity;

    @Column(name="quantidade_sim")
    private int qtdSim;

    @Column(name="quantidade_nao")
    private int qtdNao;

}
