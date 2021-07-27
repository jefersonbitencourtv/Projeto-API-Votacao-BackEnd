package api.desafio;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.domain.services.PautaService;
import api.desafio.exception.APIException;
import io.swagger.annotations.Api;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PautaServiceTests {
    @Mock
    private PautaRepository repositorio;

    @InjectMocks
    private PautaService service;

    @Test
    public void testInserirPauta() {
        //Objeto para teste
        ApiResponsePautaDTO apiResponseTeste = new ApiResponsePautaDTO();
        PautaDTO dto = new PautaDTO(15L,"aa","bb");
        apiResponseTeste.setPauta(dto);
        apiResponseTeste.setMensagem("Pauta criada");

        //Objeto da service
        PautaEntity pautaEntityEntrada = new PautaEntity();
        pautaEntityEntrada.setTitulo("aa");
        pautaEntityEntrada.setDescricao("bb");

        PautaEntity pautaEntityRetorno = new PautaEntity();
        pautaEntityRetorno.setTitulo("aa");
        pautaEntityRetorno.setDescricao("bb");
        pautaEntityRetorno.setId(15L);

        Mockito.when(repositorio.save(pautaEntityEntrada)).thenReturn(pautaEntityRetorno);

        ApiResponsePautaDTO apiResponseService;
        PautaRequest pautaRequest = new PautaRequest();
        pautaRequest.setTitulo("aa");
        pautaRequest.setDescricao("bb");
        apiResponseService = service.inserirPauta(pautaRequest);

        Assert.assertEquals(apiResponseService, apiResponseTeste);
    }
    @Test
    public void testGetPauta(){
        //Objeto do banco
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(15L);

        List<PautaEntity> listaPautaEntity = new ArrayList<>();
        listaPautaEntity.add(pautaEntity);

        Mockito.when(repositorio.findAll()).thenReturn(listaPautaEntity).thenThrow(APIException.class);
        ApiResponsePautaDTO apiResponseService;
        apiResponseService = service.getPauta();

        //Objeto para teste
        ApiResponsePautaDTO apiResponseTeste = new ApiResponsePautaDTO();
        PautaDTO pautaDTO = new PautaDTO(15L,"aa","bb");
        List<PautaDTO> listaPautaDTO = new ArrayList<>();
        listaPautaDTO.add(pautaDTO);
        apiResponseTeste.setListaPauta(listaPautaDTO);

        Assert.assertEquals(apiResponseService, apiResponseTeste);

    }
    @Test
    public void testGetPautaById(){
        //Objeto do banco
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(15L);
        Mockito.when(repositorio.findById(15L)).thenReturn(Optional.of(pautaEntity));

        ApiResponsePautaDTO apiResponseService;
        apiResponseService = service.getPautaById(15L);

        //Objeto para teste
        ApiResponsePautaDTO apiResponseTeste = new ApiResponsePautaDTO();
        PautaDTO pautaDTO = new PautaDTO(15L,"aa","bb");
        apiResponseTeste.setPauta(pautaDTO);

        Assert.assertEquals(apiResponseService, apiResponseTeste);

    }

    @Test(expected = APIException.class)
    public void testGetPautaByIdNãoEncontrada(){
        Mockito.when(service.getPautaById(1L)).thenThrow(APIException.class);
    }


    @Test(expected = APIException.class)
    public void testTituloDeveSerPreenchido(){
        PautaRequest pautaRequest = new PautaRequest();
        pautaRequest.setDescricao("aa");
        pautaRequest.setTitulo("");
        Mockito.when(service.inserirPauta(pautaRequest)).thenThrow(APIException.class);

    }
    @Test(expected = APIException.class)
    public void testDescricaoDeveSerPreenchido(){
        PautaRequest pautaRequest = new PautaRequest();
        pautaRequest.setTitulo("aa");
        pautaRequest.setDescricao("");
        Mockito.when(service.inserirPauta(pautaRequest)).thenThrow(APIException.class);

    }
}

