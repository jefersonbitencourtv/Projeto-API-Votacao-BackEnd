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
    public void testInserirAssociado() {
        //Objeto para teste
        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        AssociadoDTO associadoDTO = new AssociadoDTO("85616741051",15);
        responsePadraoTeste.setObjeto(associadoDTO);
        responsePadraoTeste.setTexto("Associado cadastrado");

        //Objeto da service
        AssociadoEntity associadoEntityEntrada = new AssociadoEntity();
        associadoEntityEntrada.setCpf("85616741051");

        AssociadoEntity associadoEntityRetorno = new AssociadoEntity();
        associadoEntityRetorno.setCpf("85616741051");
        associadoEntityRetorno.setId(15L);

        Mockito.when(repositorio.save(associadoEntityEntrada)).thenReturn(associadoEntityRetorno);
        ResponsePadrao responsePadraoService;

        AssociadoRequest request = new AssociadoRequest();
        request.setCpf("85616741051");
        responsePadraoService = service.inserirAssociado(request);

        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }
    @Test
    public void testGetAssociado(){
        //Objeto do banco
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setCpf("85616741051");
        associadoEntity.setId(0);

        List<AssociadoEntity> listaAssociadoEntity = new ArrayList<>();
        listaAssociadoEntity.add(associadoEntity);

        Mockito.when(repositorio.findAll()).thenReturn(listaAssociadoEntity);
        ResponsePadrao responsePadraoService;
        responsePadraoService = service.getAssociado();

        //Objeto para teste

        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        AssociadoDTO associadoDTO = new AssociadoDTO("85616741051",0);
        List<AssociadoDTO> listaAssociadoDTO = new ArrayList<>();
        listaAssociadoDTO.add(associadoDTO);
        responsePadraoTeste.setListaObjeto(Arrays.asList(listaAssociadoDTO.toArray()));

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);

    }
    @Test
    public void testGetAssociadoById(){
        //Objeto do banco
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setCpf("85616741051");
        associadoEntity.setId(15L);
        Mockito.when(repositorio.findById(15L)).thenReturn(Optional.of(associadoEntity));
        ResponsePadrao responsePadraoService;

        responsePadraoService = service.getAssociadoById(15L);

        //Objeto para teste
        ResponsePadrao responsePadraoTeste = new ResponsePadrao();
        AssociadoDTO associadoDTO = new AssociadoDTO("85616741051",15);
        responsePadraoTeste.setObjeto(associadoDTO);

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);

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
    public void testCPFDeveConter11Numero(){
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
