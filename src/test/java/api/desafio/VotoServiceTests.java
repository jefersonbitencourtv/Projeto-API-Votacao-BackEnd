package api.desafio;

import api.desafio.domain.dto.VotarDTO;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotarEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.repository.VotarRepository;
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
    private VotarRepository votarRepository;
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
    private VotoService votarService;

    @Test
    public void testInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("SIM",1L);

        //Objeto do banco votar
        VotarEntity votarEntityEntrada = new VotarEntity();
        votarEntityEntrada.setVoto("SIM");
        votarEntityEntrada.setIdVotacao(1L);
        votarEntityEntrada.setIdAssociado(1L);

        VotarEntity votarEntityRetorno = new VotarEntity();
        votarEntityRetorno.setVoto("SIM");
        votarEntityRetorno.setIdVotacao(1L);
        votarEntityRetorno.setIdAssociado(1L);
        votarEntityRetorno.setId(1L);

        Mockito.when(votarRepository.save(votarEntityEntrada)).thenReturn(votarEntityRetorno);

        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("SIM");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        ResponsePadrao responsePadraoService = votarService.inserirVoto(votarRequest);

        //Objeto de teste
        VotarDTO votarDTO = new VotarDTO();
        votarDTO.setVoto("SIM");
        votarDTO.setIdVotacao(1L);
        votarDTO.setIdAssociado(1);
        votarDTO.setId(1L);
        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        responsePadraoTeste.setObjeto(votarDTO);
        responsePadraoTeste.setTexto("Voto criado com sucesso");

        Assert.assertEquals(responsePadraoService,responsePadraoTeste);

    }

    @Test
    public void testGetVotos(){
        //Cria objeto do banco
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L)        ;
        List<VotarEntity> listaVotarEntity = new ArrayList<>();
        listaVotarEntity.add(votarEntity);
        Mockito.when(votarRepository.findAll()).thenReturn(listaVotarEntity);
        ResponsePadrao responsePadraoService = votarService.getVoto();

        //OBjeto para teste
        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        VotarDTO votarDTO = new VotarDTO(1L,1L,1L,"sim");
        List<VotarDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(votarDTO);
        responsePadraoTeste.setListaObjeto(Arrays.asList(listaResultadoDTO.toArray()));
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetVotosById(){
        //Cria objeto
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L);

        Mockito.when(votarRepository.findById(1L)).thenReturn(Optional.of(votarEntity));

        ResponsePadrao responsePadraoService = votarService.getVotoById(1L);

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();

        VotarDTO votarDTO = new VotarDTO(1L,1L,1L,"sim");
        responsePadraoTeste.setObjeto(votarDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test(expected = APIException.class)
    public void testVotacaoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdAssociado(1L);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testAssociadoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(1L);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotoDeveSerFornecida() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotoDeveSerSimOuNao() {
        //Cria objeto
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("a");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testAssociadoJaVotou() {
        //Votar entitity é chamada de outro metdoWW
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        VotarEntity votarEntity = new VotarEntity();
        votarEntity.setVoto("sim");
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L);
        votarEntity.setId(1L);
        Mockito.when(votarRepository.findByIdAssociadoAndIdVotacao(
                votarEntity.getIdAssociado(),votarEntity.getIdVotacao())).thenReturn(Optional.of(votarEntity));
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotacaoNaoEstaDisponivel(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDataAbertura(dataAbertura);

        //Metodos para mockar
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);

        //Construção
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }



}
