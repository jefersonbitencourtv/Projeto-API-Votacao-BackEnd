package api.desafio.domain.entities;

import api.desafio.domain.services.PautaService;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
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

    @Column(name="Data_Abertura")
    private LocalDateTime dataAbertura;


}
