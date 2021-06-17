package api.desafio;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.PautaService;
import api.desafio.domain.services.VotacaoService;
import api.desafio.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VotacaoServiceTests {
    @Mock
    private VotacaoRepository votacaoRepositorio;
    @Mock
    private PautaRepository pautaRepositorio;
    @InjectMocks
    private VotacaoService serviceVotacao;
    @Mock
    private PautaService servicePauta;

    @Test
    public void inserirVotacao() {
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(0L);
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("aa");
        pautaDTO.setDescricao("bb");
        pautaDTO.setId(0L);
        Mockito.when(pautaRepositorio.findById(0L)).thenReturn(Optional.of(pautaEntity));
        ResponsePadrao responsePadraoPauta = new ResponsePadrao();
        responsePadraoPauta.setObjeto(pautaDTO);
        Mockito.when(servicePauta.getPautaById(0L)).thenReturn(responsePadraoPauta);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        VotacaoDTO dto = new VotacaoDTO(0L,0L, 2000L, LocalDateTime.now());
        responsePadrao1.setObjeto(dto);
        responsePadrao1.setTexto("Votacao criada com sucesso");

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(LocalDateTime.now());
        Mockito.when(votacaoRepositorio.save(votacaoEntity)).thenReturn(votacaoEntity);

        ResponsePadrao responsePadrao;
        VotacaoRequest request = new VotacaoRequest();
        request.setIdPauta(0L);
        request.setDuracaoVotacao(2000L);
        responsePadrao = serviceVotacao.inserirVotacao(request);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }
    @Test(expected = APIException.class)
    public void testIDPautaDeveSerFornecido(){
        VotacaoRequest request = new VotacaoRequest();
        request.setDuracaoVotacao(200L);
        Mockito.when(serviceVotacao.inserirVotacao(request)).thenThrow(APIException.class);
    }
    @Test(expected = APIException.class)
    public void testJaExisteVotacao(){
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(0L);
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("aa");
        pautaDTO.setDescricao("bb");
        pautaDTO.setId(0L);
        Mockito.when(pautaRepositorio.findById(0L)).thenReturn(Optional.of(pautaEntity));
        ResponsePadrao responsePadraoPauta = new ResponsePadrao();
        responsePadraoPauta.setObjeto(pautaDTO);
        Mockito.when(servicePauta.getPautaById(0L)).thenReturn(responsePadraoPauta);

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(LocalDateTime.now());
        Mockito.when(votacaoRepositorio.findByIdPauta(0L)).thenReturn(Optional.of(votacaoEntity));
        VotacaoEntity votacaoEntity23 = new VotacaoEntity();
        serviceVotacao.getVotacaoByIdUsoValidacaoInsercaoResultado(0L);

        //Objeto para teste
        VotacaoRequest request = new VotacaoRequest();
        request.setIdPauta(0L);
        request.setDuracaoVotacao(2000L);


        Mockito.when(serviceVotacao.inserirVotacao(request)).thenThrow(APIException.class);
    }
    @Test
    public void testGetVotacao(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);

        List<VotacaoEntity> lista = new ArrayList<>();
        lista.add(votacaoEntity);
        Mockito.when(votacaoRepositorio.findAll()).thenReturn(lista);
        ResponsePadrao responsePadrao;
        responsePadrao = serviceVotacao.getVotacao();

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        VotacaoDTO dto = new VotacaoDTO(0L,0L,2000L,tempo);
        List<VotacaoDTO> lista1 = new ArrayList<>();
        lista1.add(dto);
        responsePadrao1.setListaObjeto(Arrays.asList(lista1.toArray()));

        Assert.assertEquals(responsePadrao, responsePadrao1);
    }

    @Test
    public void testGetVotacaoById(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);

        Mockito.when(votacaoRepositorio.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        ResponsePadrao responsePadrao;
        responsePadrao = serviceVotacao.getVotacaoById(0L);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        VotacaoDTO votacaoDTO = new VotacaoDTO(0L,0L,2000L,tempo);
        responsePadrao1.setObjeto(votacaoDTO);

        Assert.assertEquals(responsePadrao, responsePadrao1);

    }

    @Test
    public void testGetVotacaoByIdUsoValidacaoInsercaoResultado(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);

        Mockito.when(votacaoRepositorio.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        Optional<VotacaoDTO> optionalVotacao= serviceVotacao.getVotacaoByIdUsoValidacaoInsercaoResultado(0L);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        Optional<VotacaoDTO> votacaoDTO = Optional.of(new VotacaoDTO(0L, 0L, 2000L, tempo));

        Assert.assertEquals(optionalVotacao, votacaoDTO);

    }
    @Test
    public void testGetVotacaoByIdPautaUsoValidacaoInserirVotacao(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);

        Mockito.when(votacaoRepositorio.findByIdPauta(0L)).thenReturn(Optional.of(votacaoEntity));
        Optional<VotacaoEntity> optionalVotacao= serviceVotacao.getVotacaoByIdPautaUsoValidacaoInserirVotacao(0L);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        Optional<VotacaoEntity> votacaoEntity2 = Optional.of(new VotacaoEntity(0L, 0L, 2000L, tempo));

        Assert.assertEquals(optionalVotacao, votacaoEntity2);

    }
    @Test
    public void testGetDuracaoVotacaoUsoValidacaoInserirVoto(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);
        Mockito.when(votacaoRepositorio.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        Long tempo2 = serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(0L);

        //Objeto para teste
        VotacaoEntity votacaoEntity2 = new VotacaoEntity(0L, 0L, 2000L, tempo);

        Assert.assertEquals(tempo2, votacaoEntity2.getDuracaoVotacao());
    }

    @Test
    public void testGetDataAberturaUsoValidacaoInserirVoto(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);
        Mockito.when(votacaoRepositorio.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        LocalDateTime tempo2 = serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(0L);

        //Objeto para teste
        VotacaoEntity votacaoEntity2 = new VotacaoEntity(0L, 0L, 2000L, tempo);

        Assert.assertEquals(tempo2, votacaoEntity2.getDataAbertura());
    }

}
