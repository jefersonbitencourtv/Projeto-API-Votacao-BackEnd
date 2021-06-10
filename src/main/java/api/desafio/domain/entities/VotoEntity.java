package api.desafio.domain.entities;

import javax.persistence.*;

@Entity(name="votar")
public class VotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idAssociado;

    @OneToOne
    @JoinColumn(name = "Id_Votacao")
    private VotacaoEntity idVotacao;

    @Column(name = "Voto")
    private String voto;

    public VotoEntity(){}

    public VotoEntity(long id, long idAssociado, VotacaoEntity idVotacao, String voto) {
        this.id = id;
        this.idAssociado = idAssociado;
        this.idVotacao = idVotacao;
        this.voto = voto;
    }

    public long getId() {
        return id;
    }


    public long getIdAssociado() {
        return idAssociado;
    }

    public void setIdAssociado(long idAssociado) {
        this.idAssociado = idAssociado;
    }

    public VotacaoEntity getIdVotacao() {
        return idVotacao;
    }

    public void setIdVotacao(VotacaoEntity idVotacao) {
        this.idVotacao = idVotacao;
    }

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }
}
