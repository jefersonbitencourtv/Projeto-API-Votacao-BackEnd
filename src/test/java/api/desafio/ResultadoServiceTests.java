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
    public void inserirVotoNoResultadoElseAndIF(){
        LocalDateTime DataAbertura = LocalDateTime.now();
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(0L);
        votacaoEntity.setIdPauta(0L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(DataAbertura);
        ResultadoDTO dto1 = new ResultadoDTO();
        ResultadoEntity rent = new ResultadoEntity();
        //Teste do if
        Mockito.when(resultadoRepositorio.findByIdVotacao(0)).thenReturn(Optional.of(rent));
        //Teste do else
        Mockito.when(votacaoRepositorio.findByIdPauta(0L)).thenReturn(Optional.of(votacaoEntity));
        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setDuracaoVotacao(2000L);
        votacaoDTO.setId(0L);
        votacaoDTO.setIdPauta(0L);
        votacaoDTO.setDataAbertura(DataAbertura);
        Mockito.when(votacaoService.getVotacaoByIdUsoValidacaoInsercaoResultado(0L)).thenReturn(Optional.of(votacaoDTO));

        //Objeto da service
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        Mockito.when(resultadoRepositorio.save(resultadoEntity)).thenReturn(resultadoEntity);
        resultadoService.inserirVotoNoResultado("sim",0L);

    }
    @Test
    public void testGetResultadoByIdVotacao(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        Mockito.when(resultadoRepositorio.findByIdVotacao(0L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadrao = resultadoService.getResultadoByIdVotacao(0L);

        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        //responsePadrao.setObjeto();
        ResultadoDTO resultadoDTO = new ResultadoDTO(0L,0L,0L,0,0);
        responsePadrao1.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }
    @Test
    public void getResultadoByIdVotacaoUsoParaTestarInserirResultado(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        Mockito.when(resultadoRepositorio.findByIdVotacao(0L)).thenReturn(Optional.of(resultadoEntity));

        Optional<ResultadoDTO> resultadoDTO = resultadoService.getResultadoByIdVotacaoUsoParaTestarInserirResultado(0L);

        Optional<ResultadoDTO> resultadoDTO1 = Optional.of(new ResultadoDTO(0L, 0L, 0L, 0, 0));

        Assert.assertEquals(resultadoDTO,resultadoDTO1);
    }

    @Test
    public void testGetResultadoByIdPauta(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        Mockito.when(resultadoRepositorio.findByIdPauta(0L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadrao = resultadoService.getResultadoByIdPauta(0L);

        ResponsePadrao responsePadrao1 = new ResponsePadrao();

        ResultadoDTO resultadoDTO = new ResultadoDTO(0L,0L,0L,0,0);
        responsePadrao1.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }

    @Test
    public void testGetResultadoById(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        Mockito.when(resultadoRepositorio.findById(0L)).thenReturn(Optional.of(resultadoEntity));

        ResponsePadrao responsePadrao = resultadoService.getResultadoById(0L);

        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        //responsePadrao.setObjeto();
        ResultadoDTO resultadoDTO = new ResultadoDTO(0L,0L,0L,0,0);
        responsePadrao1.setObjeto(resultadoDTO);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }

    @Test
    public void testGetResultado(){
        ResultadoEntity resultadoEntity = new ResultadoEntity();
        resultadoEntity.setId(0L);
        resultadoEntity.setIdVotacao(0L);
        resultadoEntity.setIdPauta(0L);
        resultadoEntity.setQtdSim(0);
        resultadoEntity.setQtdNao(0);
        List<ResultadoEntity> lista = new ArrayList<>();
        lista.add(resultadoEntity);
        Mockito.when(resultadoRepositorio.findAll()).thenReturn(lista);

        ResponsePadrao responsePadrao = resultadoService.getResultado();

        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        //responsePadrao.setObjeto();
        ResultadoDTO resultadoDTO = new ResultadoDTO(0L,0L,0L,0,0);
        List<ResultadoDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(resultadoDTO);
        responsePadrao1.setListaObjeto(Arrays.asList(listaResultadoDTO.toArray()));
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }



}
