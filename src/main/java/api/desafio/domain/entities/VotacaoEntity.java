package api.desafio.domain.entities;

import api.desafio.domain.services.PautaService;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="votacao")
public class VotacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name ="Pauta")
    private PautaEntity pauta;

    @Column(name="Duracao_Votacao")
    private long duracaoVotacao;

    @Column(name="Quantidade_Sim")
    private int quantidadeSim;

    @Column(name="Quantidade_Nao")
    private int quantidadeNao;

    public VotacaoEntity(){}

    public VotacaoEntity(long id, PautaEntity pauta, long duracaoVotacao, int quantidadeSim, int quantidadeNao) {
        this.id = id;
        this.pauta = pauta;
        this.duracaoVotacao = duracaoVotacao;
        this.quantidadeSim = quantidadeSim;
        this.quantidadeNao = quantidadeNao;
    }

    public long getId() {
        return id;
    }

    public PautaEntity getIdPauta() {
        return pauta;
    }

    public void setIdPauta(PautaEntity idPauta) {
        this.pauta = idPauta;
    }

    public long getDuracaoVotacao() {
        return duracaoVotacao;
    }

    public void setDuracaoVotacao(long duracaoVotacao) {
        this.duracaoVotacao = duracaoVotacao;
    }

    public int getQuantidadeSim() {
        return quantidadeSim;
    }

    public void setQuantidadeSim(int quantidadeSim) {
        this.quantidadeSim = quantidadeSim;
    }

    public int getQuantidadeNao() {
        return quantidadeNao;
    }

    public void setQuantidadeNao(int quantidadeNao) {
        this.quantidadeNao = quantidadeNao;
    }
}
