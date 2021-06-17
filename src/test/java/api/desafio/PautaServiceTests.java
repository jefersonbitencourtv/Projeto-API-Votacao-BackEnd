package api.desafio;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.request.PautaRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.AssociadoService;
import api.desafio.domain.services.PautaService;
import api.desafio.exception.APIException;
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
    public void inserirPauta() {
        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        PautaDTO dto = new PautaDTO(0L,"aa","bb");
        responsePadrao1.setObjeto(dto);
        responsePadrao1.setTexto("Pauta criada");

        //Objeto da service
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        Mockito.when(repositorio.save(pautaEntity)).thenReturn(pautaEntity);
        ResponsePadrao responsePadrao;
        PautaRequest request = new PautaRequest();
        request.setTitulo("aa");
        request.setDescricao("bb");
        responsePadrao = service.inserirPauta(request);

        Assert.assertEquals(responsePadrao,responsePadrao1);
    }
    @Test
    public void testGetPauta(){
        //Objeto do banco
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(0L);
        List<PautaEntity> lista = new ArrayList<>();
        lista.add(pautaEntity);
        Mockito.when(repositorio.findAll()).thenReturn(lista);
        ResponsePadrao responsePadrao;
        responsePadrao = service.getPauta();

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        PautaDTO dto = new PautaDTO(0L,"aa","bb");
        List<PautaDTO> lista1 = new ArrayList<>();
        lista1.add(dto);
        responsePadrao1.setListaObjeto(Arrays.asList(lista1.toArray()));

        Assert.assertEquals(responsePadrao, responsePadrao1);

    }
    @Test
    public void testGetPautaById(){
        //Objeto do banco
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(0L);
        Mockito.when(repositorio.findById(0L)).thenReturn(Optional.of(pautaEntity));

        ResponsePadrao responsePadrao;
        responsePadrao = service.getPautaById(0L);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        PautaDTO pautaDTO = new PautaDTO(0L,"aa","bb");
        responsePadrao1.setObjeto(pautaDTO);

        Assert.assertEquals(responsePadrao, responsePadrao1);

    }
    @Test(expected = APIException.class)
    public void testTituloDeveSerPreenchido(){
        PautaRequest request = new PautaRequest();
        request.setDescricao("aa");
        request.setTitulo("");
        Mockito.when(service.inserirPauta(request)).thenThrow(APIException.class);

    }
    @Test(expected = APIException.class)
    public void testDescricaoDeveSerPreenchido(){
        PautaRequest request = new PautaRequest();
        request.setTitulo("aa");
        request.setDescricao("");
        Mockito.when(service.inserirPauta(request)).thenThrow(APIException.class);

    }

}

