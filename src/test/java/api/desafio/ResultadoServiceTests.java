package api.desafio;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.response.ApiResponseResultadoDTO;
import api.desafio.domain.services.ResultadoService;
import api.desafio.domain.services.VotacaoService;
import api.desafio.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
@RunWith(SpringRunner.class)
public class ResultadoServiceTests {
    @Mock
    private ResultadoRepository resultadoRepositorio;
    @Mock
    private VotacaoService votacaoService;
    @Spy
    @InjectMocks
    private ResultadoService resultadoService;


    @Test
    public void testInserirVotoNoResultadoIF(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        Optional<ResultadoEntity> resultadoEntity = Optional.of(new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1));

        Optional<ResultadoDTO> resultadoDTO = Optional.of(new ResultadoDTO(1L, 1L, 1L, 1, 1));

        Mockito.doReturn(resultadoDTO).when(resultadoService).getResultadoByIdVotacaoUsoParaTestarInserirResultado(1L);

        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L, DataAbertura);

        Mockito.when(votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(1L)).thenReturn(Optional.of(votacaoDTO));

        //Objeto da service
        ResultadoEntity resultadoEntityEntrada = new ResultadoEntity();
        resultadoEntityEntrada.setVotacaoEntity(votacaoEntity);
        resultadoEntityEntrada.setPautaEntity(pautaEntity);
        resultadoEntityEntrada.setQtdSim(1);
        resultadoEntityEntrada.setQtdNao(1);

        ResultadoEntity resultadoEntityRetorno = new ResultadoEntity(1L,votacaoEntity,pautaEntity,1,1);

        Mockito.when(resultadoRepositorio.save(resultadoEntityEntrada)).thenReturn(resultadoEntityRetorno);

        resultadoService.inserirVotoNoResultado("SIM",1L);
        resultadoService.inserirVotoNoResultado("NAO",1L);
        Mockito.verify(resultadoService,Mockito.times(1)).inserirVotoNoResultado("SIM",1L);
        Mockito.verify(resultadoService,Mockito.times(1)).inserirVotoNoResultado("NAO",1L);

    }

    @Test
    public void testInserirVotoNoResultadoElse(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        VotacaoDTO votacaoDTO = new VotacaoDTO(1l,1l,2000L,DataAbertura);
        Mockito.when(votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(1L)).thenReturn(Optional.of(votacaoDTO));

        //Objeto da service
        ResultadoEntity resultadoEntityEntrada = new ResultadoEntity();
        resultadoEntityEntrada.setVotacaoEntity(votacaoEntity);
        resultadoEntityEntrada.setPautaEntity(pautaEntity);
        resultadoEntityEntrada.setQtdSim(1);
        resultadoEntityEntrada.setQtdNao(1);

        ResultadoEntity resultadoEntityRetorno = new ResultadoEntity(1L,votacaoEntity,pautaEntity,1,1);

        Mockito.when(resultadoRepositorio.save(resultadoEntityEntrada)).thenReturn(resultadoEntityRetorno);

        resultadoService.inserirVotoNoResultado("SIM",1L);
        resultadoService.inserirVotoNoResultado("NAO",1L);
        Mockito.verify(resultadoService,Mockito.times(1)).inserirVotoNoResultado("SIM",1L);
        Mockito.verify(resultadoService,Mockito.times(1)).inserirVotoNoResultado("NAO",1L);

    }
    @Test
    public void testGetResultadoByIdVotacao(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1);
        Mockito.when(resultadoRepositorio.findByVotacaoEntity(Mockito.any())).thenReturn(Optional.of(resultadoEntity));

        ApiResponseResultadoDTO responsePadraoService = resultadoService.getResultadoByIdVotacao(1L);

        ApiResponseResultadoDTO responsePadraoTeste = new ApiResponseResultadoDTO();
        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setResultado(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }
    @Test
    public void testGetResultadoByIdVotacaoUsoParaTestarInserirResultado(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1);
        Mockito.when(resultadoRepositorio.findByVotacaoEntity(Mockito.any())).thenReturn(Optional.of(resultadoEntity));

        Optional<ResultadoDTO> resultadoDTOEntrada = resultadoService.getResultadoByIdVotacaoUsoParaTestarInserirResultado(1L);

        Optional<ResultadoDTO> resultadoDTOTeste = Optional.of(new ResultadoDTO(1L, 1L, 1L, 1, 1));

        Assert.assertEquals(resultadoDTOEntrada,resultadoDTOTeste);
    }

    @Test
    public void testGetResultadoByIdPauta(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1);
        Mockito.when(resultadoRepositorio.findByPautaEntity(Mockito.any())).thenReturn(Optional.of(resultadoEntity));

        ApiResponseResultadoDTO responsePadraoService = resultadoService.getResultadoByIdPauta(1L);

        ApiResponseResultadoDTO responsePadraoTeste = new ApiResponseResultadoDTO();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setResultado(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetResultadoById(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1);
        Mockito.when(resultadoRepositorio.findById(1L)).thenReturn(Optional.of(resultadoEntity));

        ApiResponseResultadoDTO responsePadraoService = resultadoService.getResultadoById(1L);

        ApiResponseResultadoDTO responsePadraoTeste = new ApiResponseResultadoDTO();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setResultado(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }
    @Test(expected = APIException.class)
    public void testGetResultadoByIdNãoEncontrado(){
        Mockito.when(resultadoService.getResultadoById(1L)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testGetResultadoByIdPautaNãoEncontrado(){
        Mockito.when(resultadoService.getResultadoByIdPauta(1L)).thenThrow(APIException.class);
    }

    @Test
    public void testGetResultado(){
        LocalDateTime DataAbertura = LocalDateTime.now();

        //Cria objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L, "AA", "AA");

        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L, pautaEntity,2000L, DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity(1L, votacaoEntity, pautaEntity, 1, 1);

        List<ResultadoEntity> resultadoEntityList = new ArrayList<>();
        resultadoEntityList.add(resultadoEntity);
        Mockito.when(resultadoRepositorio.findAll()).thenReturn(resultadoEntityList);

        ApiResponseResultadoDTO responsePadraoService = resultadoService.getResultado();

        ApiResponseResultadoDTO responsePadraoTeste = new ApiResponseResultadoDTO();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        List<ResultadoDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(resultadoDTO);
        responsePadraoTeste.setListaResultado(listaResultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

}
