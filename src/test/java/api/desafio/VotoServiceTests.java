package api.desafio;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.AssociadoService;
import api.desafio.domain.services.ResultadoService;
import api.desafio.domain.services.VotacaoService;
import api.desafio.domain.services.VotoService;
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
public class VotoServiceTests {
    @Mock
    private VotoRepository votoRepository;
    @Mock
    private AssociadoService associadoService;
    @Mock
    private VotacaoService votacaoService;
    @Mock
    private ResultadoService resultadoService;
    @Mock
    private VotacaoRepository votacaoRepository;
    @Mock
    private ResultadoRepository resultadoRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @InjectMocks
    private VotoService votoService;

    @Test
    public void testInserirVoto(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(tempo);
        Mockito.when(votacaoRepository.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(0L)).thenReturn(tempo);
        Long custo = 2000L;
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(0L)).thenReturn(custo);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("Sim",0L);

        //Objeto do banco votar
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(0L);
        votarRequest.setIdAssociado(0L);
        ResponsePadrao responsePadrao = votoService.inserirVoto(votarRequest);

        //Objeto de teste
        VotarDTO votarDTO = new VotarDTO();
        votarDTO.setVoto("SIM");
        votarDTO.setIdVotacao(0L);
        votarDTO.setIdAssociado(0);
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        responsePadrao1.setObjeto(votarDTO);
        responsePadrao1.setTexto("Voto criado com sucesso");

        Assert.assertEquals(responsePadrao,responsePadrao1);

    }

    @Test
    public void testGetVotos(){
        //Cria objeto do banco
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(0L);
        votarEntity.setIdVotacao(0L);
        votarEntity.setIdAssociado(0L)        ;
        List<VotarEntity> lista = new ArrayList<>();
        lista.add(votarEntity);
        Mockito.when(votoRepository.findAll()).thenReturn(lista);
        ResponsePadrao responsePadrao = votoService.getVoto();

        //OBjeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        VotarDTO votarDTO = new VotarDTO(0L,0L,0L,"sim");
        List<VotarDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(votarDTO);
        responsePadrao1.setListaObjeto(Arrays.asList(listaResultadoDTO.toArray()));
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }

    @Test
    public void testGetVotosById(){
        //Cria objeto
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(0L);
        votarEntity.setIdVotacao(0L);
        votarEntity.setIdAssociado(0L);

        Mockito.when(votoRepository.findById(0L)).thenReturn(Optional.of(votarEntity));

        ResponsePadrao responsePadrao = votoService.getVotoById(0L);

        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        //responsePadrao.setObjeto();
        VotarDTO votarDTO = new VotarDTO(0L,0L,0L,"sim");
        responsePadrao1.setObjeto(votarDTO);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }

    @Test(expected = APIException.class)
    public void testVotacaoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdAssociado(0L);
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testAssociadoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(0L);
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setIdVotacao(0L);
        votarRequest.setIdAssociado(0L);
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotoDeveSerSimOuNao() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("a");
        votarRequest.setIdVotacao(0L);
        votarRequest.setIdAssociado(0L);
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testAssociadoJaVotou() {
        //Votar entitity é chamada de outro metdoWW
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(0L);
        votarRequest.setIdAssociado(0L);
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setIdVotacao(0L);
        votarEntity.setIdAssociado(0L);
        votarEntity.setId(0L);
        Mockito.when(votoRepository.findByIdAssociadoAndIdVotacao(
                votarEntity.getIdAssociado(),votarEntity.getIdVotacao())).thenReturn(Optional.of(votarEntity));
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotacaoNaoEstaDisponivel(){
        LocalDateTime tempo = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDataAbertura(LocalDateTime.now());

        //Metodos para mockar
        Mockito.when(votacaoRepository.findById(0L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(0L)).thenReturn(LocalDateTime.now());
        Long custo = 1L;
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(0L)).thenReturn(custo);

        //Construção
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setIdVotacao(0L);
        votarRequest.setIdAssociado(0L);
        Mockito.when(votoService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }



}
