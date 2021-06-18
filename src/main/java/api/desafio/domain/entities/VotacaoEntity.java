package api.desafio.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private long idPauta;

    @Column(name="Duracao_Votacao")
    private Long duracaoVotacao;

    @Column(name="Data_Abertura")
    private LocalDateTime dataAbertura;

}
