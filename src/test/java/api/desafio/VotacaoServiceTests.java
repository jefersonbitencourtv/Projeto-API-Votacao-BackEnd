package api.desafio;

import api.desafio.domain.dto.PautaDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.request.VotacaoRequest;
import api.desafio.domain.response.ApiResponse;
import api.desafio.domain.response.ApiResponsePautaDTO;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.domain.services.PautaService;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class VotacaoServiceTests {
    @Mock
    private VotacaoRepository votacaoRepository;
    @InjectMocks
    @Spy
    private VotacaoService serviceVotacao;
    @Mock
    private PautaService servicePauta;

    @Test
    public void testInserirVotacao() {
        LocalDateTime DataAbertura = LocalDateTime.now();
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L,"aa","bb");

        PautaDTO pautaDTO = new PautaDTO(1L,"aa","bb");

        //Mockito.when(pautaRepositorio.findById(1L)).thenReturn(Optional.of(pautaEntity));
        ApiResponsePautaDTO responsePadraoPauta = new ApiResponsePautaDTO();
        responsePadraoPauta.setPauta(pautaDTO);
        Mockito.when(servicePauta.getPautaById(1L)).thenReturn(responsePadraoPauta);

        //Objeto para teste
        ApiResponseVotacaoDTO responsePadraoTeste = new ApiResponseVotacaoDTO();
        VotacaoDTO dto = new VotacaoDTO(1L,1L, 0L, DataAbertura);
        responsePadraoTeste.setVotacao(dto);
        responsePadraoTeste.setMensagem("Votacao criada com sucesso");

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L,pautaEntity,0L,DataAbertura);

        Mockito.when(votacaoRepository.save(Mockito.any())).thenReturn(votacaoEntity);

        ApiResponseVotacaoDTO responsePadraoService;
        VotacaoRequest request = new VotacaoRequest(1L,0L);

        responsePadraoService = serviceVotacao.inserirVotacao(request);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test(expected = APIException.class)
    public void testInserirVotacaoJaExisteVotacao() {
        LocalDateTime DataAbertura = LocalDateTime.now();
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity(1L,"aa","bb");

        PautaDTO pautaDTO = new PautaDTO(1L,"aa","bb");
        Optional<VotacaoEntity> votacaoEntity = Optional.of(new VotacaoEntity(1L, pautaEntity, 0L, DataAbertura));
        //Mockito.when(pautaRepositorio.findById(1L)).thenReturn(Optional.of(pautaEntity));
        ApiResponsePautaDTO responsePadraoPauta = new ApiResponsePautaDTO();
        responsePadraoPauta.setPauta(pautaDTO);
        Mockito.when(servicePauta.getPautaById(1L)).thenReturn(responsePadraoPauta);
        Mockito.doReturn(votacaoEntity).when(serviceVotacao).getVotacaoByIdPautaUsoValidacaoInserirVotacao(1L);

        VotacaoRequest request = new VotacaoRequest(1L,0L);

        serviceVotacao.inserirVotacao(request);
        Mockito.verify(serviceVotacao,Mockito.times(1)).inserirVotacao(request);

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
        PautaEntity pautaEntity = new PautaEntity(1L,"aa","bb");

        PautaDTO pautaDTO = new PautaDTO(1L,"aa","bb");

        //Mockito.when(pautaRepositorio.findById(1L)).thenReturn(Optional.of(pautaEntity));
        ApiResponsePautaDTO responsePadraoPauta = new ApiResponsePautaDTO();
        responsePadraoPauta.setPauta(pautaDTO);
        Mockito.when(servicePauta.getPautaById(1L)).thenReturn(responsePadraoPauta);

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(LocalDateTime.now());
        Mockito.when(votacaoRepository.findByPauta(pautaEntity)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(serviceVotacao.getVotacaoByIdPautaUsoValidacaoInserirVotacao(1L)).thenThrow(APIException.class);
        //Objeto para teste
        VotacaoRequest request = new VotacaoRequest();
        request.setIdPauta(1L);
        request.setDuracaoVotacao(2000L);


        Mockito.when(serviceVotacao.inserirVotacao(request)).thenThrow(APIException.class);
    }
    @Test
    public void testGetVotacao(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        List<VotacaoEntity> votacaoEntityList = new ArrayList<>();
        votacaoEntityList.add(votacaoEntity);
        Mockito.when(votacaoRepository.findAll()).thenReturn(votacaoEntityList);
        ApiResponse responsePadraoService;
        responsePadraoService = serviceVotacao.getVotacao();

        //Objeto para teste
        ApiResponseVotacaoDTO responsePadraoTeste = new ApiResponseVotacaoDTO();
        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L,dataAbertura);
        List<VotacaoDTO> listaVotacaoDTO = new ArrayList<>();
        listaVotacaoDTO.add(votacaoDTO);
        responsePadraoTeste.setListaVotacao(listaVotacaoDTO);

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);
    }

    @Test
    public void testGetVotacaoById(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        ApiResponseVotacaoDTO responsePadraoService;
        responsePadraoService = serviceVotacao.getVotacaoById(1L);

        //Objeto para teste
        ApiResponseVotacaoDTO responsePadraoTeste = new ApiResponseVotacaoDTO();
        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L,dataAbertura);
        responsePadraoTeste.setVotacao(votacaoDTO);

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);

    }

    @Test
    public void testGetVotacaoByIdUsoValidacaoInsercaoResultado(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Optional<VotacaoDTO> optionalVotacaoDTOService= serviceVotacao.getVotacaoByIdUsoValidacaoInsercaoResultado(1L);

        //Objeto para teste
        Optional<VotacaoDTO> OptionalVotacaoDTOTeste = Optional.of(new VotacaoDTO(1L, 1L, 2000L, dataAbertura));

        Assert.assertEquals(optionalVotacaoDTOService, OptionalVotacaoDTOTeste);

    }
    @Test
    public void testGetVotacaoByIdPautaUsoValidacaoInserirVotacao(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        Mockito.when(votacaoRepository.findByPauta(pautaEntity)).thenReturn(Optional.of(votacaoEntity));
        Optional<VotacaoEntity> optionalVotacaoEntityService= serviceVotacao.getVotacaoByIdPautaUsoValidacaoInserirVotacao(1L);

        //Objeto para teste
        Optional<VotacaoEntity> OptionalVotacaoEntityTeste = Optional.of(new VotacaoEntity(1L, pautaEntity, 2000L, dataAbertura));

        Assert.assertEquals(optionalVotacaoEntityService, OptionalVotacaoEntityTeste);

    }
    @Test
    public void testGetDuracaoVotacaoUsoValidacaoInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Long duracaoVotacaoService = serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(1L);

        //Objeto para teste
        VotacaoEntity votacaoEntityTeste = new VotacaoEntity(1L, pautaEntity, 2000L, dataAbertura);

        Assert.assertEquals(duracaoVotacaoService, votacaoEntityTeste.getDuracaoVotacao());
    }

    @Test
    public void testGetDataAberturaUsoValidacaoInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        LocalDateTime dataAberturaService = serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(1L);

        //Objeto para teste
        VotacaoEntity VotacaoEntityTeste = new VotacaoEntity(1L, pautaEntity, 2000L, dataAbertura);

        Assert.assertEquals(dataAberturaService, VotacaoEntityTeste.getDataAbertura());
    }
}
