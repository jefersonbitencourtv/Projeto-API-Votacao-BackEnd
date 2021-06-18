package api.desafio;

import api.desafio.domain.dto.ResultadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.ResultadoEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.ResultadoRequest;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.ResultadoService;
import api.desafio.domain.services.VotacaoService;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResultadoServiceTests {
    @Mock
    public ResultadoRepository resultadoRepositorio;
    @Mock
    public VotacaoService votacaoService;
    @Mock
    public VotacaoRepository votacaoRepositorio;
    @InjectMocks
    public ResultadoService resultadoService;
    @Test
    public void testInserirVotoNoResultadoElseAndIF(){
        LocalDateTime DataAbertura = LocalDateTime.now();
        //Cria objeto de votação
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(DataAbertura);

        //Objeto da service, validação
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        //Teste do if se já há resultado para a votação
        Mockito.when(resultadoRepositorio.findByIdVotacao(1)).thenReturn(Optional.of(resultadoEntity));
        //Teste do else se existe votação para a pauta
        Mockito.when(votacaoRepositorio.findByIdPauta(1L)).thenReturn(Optional.of(votacaoEntity));
        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setDuracaoVotacao(2000L);
        votacaoDTO.setId(1L);
        votacaoDTO.setIdPauta(1L);
        votacaoDTO.setDataAbertura(DataAbertura);
        Mockito.when(votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(1L)).thenReturn(Optional.of(votacaoDTO));

        //Objeto da service
        ResultadoEntity resultadoEntityEntrada = new ResultadoEntity();
        resultadoEntityEntrada.setIdVotacao(1L);
        resultadoEntityEntrada.setIdPauta(1L);
        resultadoEntityEntrada.setQtdSim(1);
        resultadoEntityEntrada.setQtdNao(1);

        ResultadoEntity resultadoEntityRetorno = new ResultadoEntity();
        resultadoEntityRetorno.setId(1L);
        resultadoEntityRetorno.setIdVotacao(1L);
        resultadoEntityRetorno.setIdPauta(1L);
        resultadoEntityRetorno.setQtdSim(1);
        resultadoEntityRetorno.setQtdNao(1);
        Mockito.when(resultadoRepositorio.save(resultadoEntityEntrada)).thenReturn(resultadoEntityRetorno);
        resultadoService.inserirVotoNoResultado("sim",1L);

    }
    @Test
    public void testGetResultadoByIdVotacao(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(1L);
        resultadoEntity.setIdVotacao(1L);
        resultadoEntity.setIdPauta(1L);
        resultadoEntity.setQtdSim(1);
        resultadoEntity.setQtdNao(1);
        Mockito.when(resultadoRepositorio.findByIdVotacao(1L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadraoService = resultadoService.getResultadoByIdVotacao(1L);

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }
    @Test
    public void testGetResultadoByIdVotacaoUsoParaTestarInserirResultado(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(1L);
        resultadoEntity.setIdVotacao(1L);
        resultadoEntity.setIdPauta(1L);
        resultadoEntity.setQtdSim(1);
        resultadoEntity.setQtdNao(1);
        Mockito.when(resultadoRepositorio.findByIdVotacao(1L)).thenReturn(Optional.of(resultadoEntity));

        Optional<ResultadoDTO> resultadoDTOEntrada = resultadoService.getResultadoByIdVotacaoUsoParaTestarInserirResultado(1L);

        Optional<ResultadoDTO> resultadoDTOTeste = Optional.of(new ResultadoDTO(1L, 1L, 1L, 1, 1));

        Assert.assertEquals(resultadoDTOEntrada,resultadoDTOTeste);
    }

    @Test
    public void testGetResultadoByIdPauta(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(1L);
        resultadoEntity.setIdVotacao(1L);
        resultadoEntity.setIdPauta(1L);
        resultadoEntity.setQtdSim(1);
        resultadoEntity.setQtdNao(1);
        Mockito.when(resultadoRepositorio.findByIdPauta(1L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadraoService = resultadoService.getResultadoByIdPauta(1L);

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetResultadoById(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(1L);
        resultadoEntity.setIdVotacao(1L);
        resultadoEntity.setIdPauta(1L);
        resultadoEntity.setQtdSim(1);
        resultadoEntity.setQtdNao(1);
        Mockito.when(resultadoRepositorio.findById(1L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadraoService = resultadoService.getResultadoById(1L);

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        responsePadraoTeste.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetResultado(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(1L);
        resultadoEntity.setIdVotacao(1L);
        resultadoEntity.setIdPauta(1L);
        resultadoEntity.setQtdSim(1);
        resultadoEntity.setQtdNao(1);
        List<ResultadoEntity> resultadoEntityList = new ArrayList<>();
        resultadoEntityList.add(resultadoEntity);
        Mockito.when(resultadoRepositorio.findAll()).thenReturn(resultadoEntityList);

        ResponsePadrao responsePadraoService = resultadoService.getResultado();

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();

        ResultadoDTO resultadoDTO = new ResultadoDTO(1L,1L,1L,1,1);
        List<ResultadoDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(resultadoDTO);
        responsePadraoTeste.setListaObjeto(Arrays.asList(listaResultadoDTO.toArray()));
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }



}
