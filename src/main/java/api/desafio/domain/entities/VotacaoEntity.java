package api.desafio.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Entity(name="votacao")
public class VotacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private long id;

    @OneToOne
    @JoinColumn(name ="Id_Pauta", referencedColumnName= "Id")
    private PautaEntity pauta;

    //@Column(name="Id_Pauta")
    //private long idPauta;

    @Column(name="Duracao_Votacao")
    private Long duracaoVotacao;

    @Column(name="Data_Abertura")
    private LocalDateTime dataAbertura;

}
