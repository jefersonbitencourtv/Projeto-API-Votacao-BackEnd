package api.desafio.domain.entities;

import api.desafio.domain.services.PautaService;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="votacao")
public class VotacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    //@OneToOne
    //@JoinColumn(name ="Id_Pauta", referencedColumnName= "Id")
    //private PautaEntity idPauta;


    @Column(name="Id_Pauta")
    private Long idPauta;

    @Column(name="Duracao_Votacao")
    private Long duracaoVotacao;

    @Column(name="Quantidade_Sim")
    private int quantidadeSim;

    @Column(name="Quantidade_Nao")
    private int quantidadeNao;

    @Column(name="Data_Abertura")
    private LocalDateTime dataAbertura;

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public VotacaoEntity(){}

    public VotacaoEntity(long id, Long idPauta,LocalDateTime dataAbertura, long duracaoVotacao, int quantidadeSim, int quantidadeNao) {
        this.id = id;
        this.dataAbertura = dataAbertura;
        this.idPauta = idPauta;
        this.duracaoVotacao = duracaoVotacao;
        this.quantidadeSim = quantidadeSim;
        this.quantidadeNao = quantidadeNao;
    }

    public long getId() {
        return id;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }

    public Long getDuracaoVotacao() {
        return duracaoVotacao;
    }

    public void setDuracaoVotacao(Long duracaoVotacao) {
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
