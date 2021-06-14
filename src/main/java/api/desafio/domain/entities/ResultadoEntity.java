package api.desafio.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Resultado")
public class ResultadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="id_votacao")
    private long idVotacao;

    @Column(name="id_pauta")
    private long idPauta;

    @Column(name="quantidade_sim")
    private int qtdSim;

    @Column(name="quantidade_nao")
    private int qtdNao;

}
