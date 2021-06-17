package api.desafio;
import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.request.AssociadoRequest;
import api.desafio.domain.response.ResponsePadrao;
import api.desafio.domain.services.AssociadoService;
import api.desafio.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class AssociadoServiceTests {
    @Mock
    private AssociadoRepository repositorio;

    @InjectMocks
    private AssociadoService service;

    @Test
    public void inserirAssociado() {
        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        AssociadoDTO dto = new AssociadoDTO("85616741051",0);
        responsePadrao1.setObjeto(dto);
        responsePadrao1.setTexto("Associado cadastrado");

        //Objeto da service
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setCpf("85616741051");
        Mockito.when(repositorio.save(associadoEntity)).thenReturn(associadoEntity);
        ResponsePadrao responsePadrao;
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("85616741051");
        responsePadrao = service.inserirAssociado(request);
        Assert.assertEquals(responsePadrao,responsePadrao1);
    }
    @Test
    public void testGetAssociado(){
        //Objeto do banco
        AssociadoEntity associado1 = new AssociadoEntity();
        associado1.setCpf("85616741051");
        associado1.setId(0);
        List<AssociadoEntity> lista = new ArrayList<>();
        lista.add(associado1);
        Mockito.when(repositorio.findAll()).thenReturn(lista);
        ResponsePadrao responsePadrao;
        responsePadrao = service.getAssociado();

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        AssociadoDTO dto = new AssociadoDTO("85616741051",0);
        List<AssociadoDTO> lista1 = new ArrayList<>();
        lista1.add(dto);
        responsePadrao1.setListaObjeto(Arrays.asList(lista1.toArray()));

        Assert.assertEquals(responsePadrao, responsePadrao1);

    }
    @Test
    public void testGetAssociadoById(){
        //Objeto do banco
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setCpf("85616741051");
        associadoEntity.setId(0L);
        Mockito.when(repositorio.findById(0L)).thenReturn(Optional.of(associadoEntity));
        ResponsePadrao responsePadrao;
        responsePadrao = service.getAssociadoById(0L);

        //Objeto para teste
        ResponsePadrao responsePadrao1 = new ResponsePadrao();
        AssociadoDTO associadoDto = new AssociadoDTO("85616741051",0);
        responsePadrao1.setObjeto(associadoDto);

        Assert.assertEquals(responsePadrao, responsePadrao1);

    }
    @Test(expected = APIException.class)
    public void testCPFDeveSerPreenchido(){
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("");
        Mockito.when(service.inserirAssociado(request)).thenThrow(APIException.class);
    }
    @Test(expected = APIException.class)
    public void testCPFDeveSerNumero(){
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("aa");
        Mockito.when(service.inserirAssociado(request)).thenThrow(APIException.class);
    }
    @Test(expected = APIException.class)
    public void testCPFDeveConterNumero(){
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("22");
        Mockito.when(service.inserirAssociado(request)).thenThrow(APIException.class);
    }
    @Test(expected = APIException.class)
    public void testCPFInvalido(){
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("12345678901");
        Mockito.when(service.inserirAssociado(request)).thenThrow(APIException.class);
    }
    @Test(expected = APIException.class)
    public void testCPFJáExiste(){
        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("85616741051");

        //Instancia retorno como se já ouvesse esse cpf no banco
        Optional<AssociadoEntity> associadoEntity = Optional.of(new AssociadoEntity());
        Mockito.when(repositorio.findByCpf("85616741051")).thenReturn(associadoEntity);

        Mockito.when(service.inserirAssociado(request)).thenThrow(APIException.class);
    }






}
