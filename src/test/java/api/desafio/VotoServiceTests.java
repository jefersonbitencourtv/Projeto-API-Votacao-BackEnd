package api.desafio;

import api.desafio.domain.dto.AssociadoDTO;
import api.desafio.domain.dto.VotacaoDTO;
import api.desafio.domain.dto.VotoDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.entities.PautaEntity;
import api.desafio.domain.entities.VotacaoEntity;
import api.desafio.domain.entities.VotoEntity;
import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.request.VotarRequest;
import api.desafio.domain.response.ApiResponseAssociadoDTO;
import api.desafio.domain.response.ApiResponseVotacaoDTO;
import api.desafio.domain.response.ApiResponseVotoDTO;
import api.desafio.domain.services.AssociadoService;
import api.desafio.domain.services.ResultadoService;
import api.desafio.domain.services.VotacaoService;
import api.desafio.domain.services.VotoService;
import api.desafio.domain.services.apiCpf.ApiCpfService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private ApiCpfService apiCpfService;

    @InjectMocks
    private VotoService votarService;

    @Test
    public void testInserirVotoSim(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity(1L,"aa","bb");
       //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L,pautaEntity,2000L,dataAbertura);

        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("SIM",1L);

        //Objeto do banco votar
        AssociadoEntity associadoEntity = new AssociadoEntity(1L,"87280549039");

        VotoEntity votarEntityEntrada = new VotoEntity();
        votarEntityEntrada.setVoto("SIM");
        votarEntityEntrada.setVotacaoEntity(votacaoEntity);
        votarEntityEntrada.setAssociadoEntity(associadoEntity);

        VotoEntity votarEntityRetorno = new VotoEntity(1L,associadoEntity,votacaoEntity,"SIM");

        Mockito.when(votoRepository.save(Mockito.any())).thenReturn(votarEntityRetorno);
        //serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();

        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L, dataAbertura);
        apiResponseVotacaoDTO.setVotacao(votacaoDTO);

        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId(1L);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        Mockito.when(votacaoService.getVotacaoById(1L)).thenReturn(apiResponseVotacaoDTO);
        Mockito.when(associadoService.getAssociadoById(1L)).thenReturn(apiResponseAssociadoDTO);

        associadoDTO.setCpf("87280549039");
        String responseApi = "ABLE_TO_VOTE";
        Mockito.when(apiCpfService.verificaCpf(associadoDTO.getCpf())).thenReturn(responseApi);

        VotarRequest votarRequest = new VotarRequest(1L,1L,"SIM");
        ApiResponseVotoDTO responsePadraoService = votarService.inserirVoto(votarRequest);

        //Objeto de teste
        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"SIM");
        ApiResponseVotoDTO responsePadraoTeste = new ApiResponseVotoDTO();
        responsePadraoTeste.setVoto(votarDTO);
        responsePadraoTeste.setMensagem("Voto criado com sucesso");

        Assert.assertEquals(responsePadraoService,responsePadraoTeste);

    }
    @Test(expected = APIException.class)
    public void testInserirVotoAssociadoJaVotou(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
       //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);

        //Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("NÃO",1L);

        //Objeto do banco votar
        AssociadoEntity associadoEntity = new AssociadoEntity(1L,"87280549039");

        VotoEntity votarEntityEntrada = new VotoEntity();
        votarEntityEntrada.setVoto("NÃO");
        votarEntityEntrada.setVotacaoEntity(votacaoEntity);
        votarEntityEntrada.setAssociadoEntity(associadoEntity);

        VotoEntity votarEntityRetorno = new VotoEntity(1L,associadoEntity,votacaoEntity,"NÃO");

        Mockito.when(votoRepository.save(Mockito.any())).thenReturn(votarEntityRetorno);
        //serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setId(1L);
        apiResponseVotacaoDTO.setVotacao(votacaoDTO);

        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId(1L);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        Mockito.when(votacaoService.getVotacaoById(1L)).thenReturn(apiResponseVotacaoDTO);
        Mockito.when(associadoService.getAssociadoById(1L)).thenReturn(apiResponseAssociadoDTO);

        associadoDTO.setCpf("87280549039");
        String responseApi = "ABLE_TO_VOTE";
        Mockito.when(apiCpfService.verificaCpf(associadoDTO.getCpf())).thenReturn(responseApi);

        VotarRequest votarRequest = new VotarRequest(1L,1L,"NÃO");
        Optional<VotoEntity> votarEntityRetorno1 = Optional.of(votarEntityRetorno);

        Mockito.when(votoRepository.findByAssociadoEntityAndVotacaoEntity(Mockito.any(), Mockito.any())).thenReturn(votarEntityRetorno1);
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);


    }@Test(expected = APIException.class)
    public void testInserirVotoAssociadoUnableToVote(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
       //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);

        //Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("NÃO",1L);

        //Objeto do banco votar
        AssociadoEntity associadoEntity = new AssociadoEntity(1L,"87280549039");

        VotoEntity votarEntityEntrada = new VotoEntity();
        votarEntityEntrada.setVoto("NÃO");
        votarEntityEntrada.setVotacaoEntity(votacaoEntity);
        votarEntityEntrada.setAssociadoEntity(associadoEntity);

        VotoEntity votarEntityRetorno = new VotoEntity(1L,associadoEntity,votacaoEntity,"NÃO");

        Mockito.when(votoRepository.save(Mockito.any())).thenReturn(votarEntityRetorno);
        //serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setId(1L);
        apiResponseVotacaoDTO.setVotacao(votacaoDTO);

        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId(1L);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        Mockito.when(votacaoService.getVotacaoById(1L)).thenReturn(apiResponseVotacaoDTO);
        Mockito.when(associadoService.getAssociadoById(1L)).thenReturn(apiResponseAssociadoDTO);

        associadoDTO.setCpf("87280549039");
        String responseApi = "UNABLE_TO_VOTE";
        Mockito.when(apiCpfService.verificaCpf(associadoDTO.getCpf())).thenReturn(responseApi);

        VotarRequest votarRequest = new VotarRequest(1L,1L,"NÃO");
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);


    }

    @Test
    public void testInserirVotoNao(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        PautaEntity pautaEntity = new PautaEntity(1L,"aa","bb");
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity(1L,pautaEntity,2000L,dataAbertura);

        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("NÃO",1L);

        //Objeto do banco votar
        AssociadoEntity associadoEntity = new AssociadoEntity(1L,"87280549039");

        VotoEntity votarEntityEntrada = new VotoEntity();
        votarEntityEntrada.setVoto("NÃO");
        votarEntityEntrada.setVotacaoEntity(votacaoEntity);
        votarEntityEntrada.setAssociadoEntity(associadoEntity);

        VotoEntity votarEntityRetorno = new VotoEntity(1L,associadoEntity,votacaoEntity,"NÃO");

        Mockito.when(votoRepository.save(Mockito.any())).thenReturn(votarEntityRetorno);
        //serviceAssociado.getAssociadoById(votarRequest.getIdAssociado());
        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();

        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L, dataAbertura);
        apiResponseVotacaoDTO.setVotacao(votacaoDTO);

        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId(1L);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        Mockito.when(votacaoService.getVotacaoById(1L)).thenReturn(apiResponseVotacaoDTO);
        Mockito.when(associadoService.getAssociadoById(1L)).thenReturn(apiResponseAssociadoDTO);

        associadoDTO.setCpf("87280549039");
        String responseApi = "ABLE_TO_VOTE";
        Mockito.when(apiCpfService.verificaCpf(associadoDTO.getCpf())).thenReturn(responseApi);

        VotarRequest votarRequest = new VotarRequest(1L,1L,"NÃO");
        ApiResponseVotoDTO responsePadraoService = votarService.inserirVoto(votarRequest);

        //Objeto de teste
        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"NÃO");
        ApiResponseVotoDTO responsePadraoTeste = new ApiResponseVotoDTO();
        responsePadraoTeste.setVoto(votarDTO);
        responsePadraoTeste.setMensagem("Voto criado com sucesso");

        Assert.assertEquals(responsePadraoService,responsePadraoTeste);

    }

    @Test
    public void testGetVotos(){
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        //Objeto do associado
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setId(1L);

        //Cria objeto do banco
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setVotacaoEntity(votacaoEntity);
        votarEntity.setAssociadoEntity(associadoEntity);        ;
        List<VotoEntity> listaVotarEntity = new ArrayList<>();
        listaVotarEntity.add(votarEntity);
        Mockito.when(votoRepository.findAll()).thenReturn(listaVotarEntity);
        ApiResponseVotoDTO responsePadraoService = votarService.getVoto();

        //OBjeto para teste
        ApiResponseVotoDTO responsePadraoTeste = new ApiResponseVotoDTO();
        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"sim");
        List<VotoDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(votarDTO);
        responsePadraoTeste.setListaVoto(listaResultadoDTO);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetVotosById(){
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        //Objeto do associado
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setId(1L);
        //Cria objeto
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setVotacaoEntity(votacaoEntity);
        votarEntity.setAssociadoEntity(associadoEntity);

        Mockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(votarEntity));

        ApiResponseVotoDTO responsePadraoService = votarService.getVotoById(1L);

        ApiResponseVotoDTO responsePadraoTeste = new ApiResponseVotoDTO();

        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"sim");
        responsePadraoTeste.setVoto(votarDTO);
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
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        //Objeto do associado
        AssociadoEntity associadoEntity = new AssociadoEntity();
        associadoEntity.setId(1L);

        //Votar entitity é chamada de outro metdoWW
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setVotacaoEntity(votacaoEntity);
        votarEntity.setAssociadoEntity(associadoEntity);
        votarEntity.setId(1L);

        ApiResponseAssociadoDTO apiResponseAssociadoDTO = new ApiResponseAssociadoDTO();
        ApiResponseVotacaoDTO apiResponseVotacaoDTO = new ApiResponseVotacaoDTO();

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setDuracaoVotacao(votacaoEntity.getDuracaoVotacao());
        votacaoDTO.setIdPauta(votacaoEntity.getPauta().getId());
        votacaoDTO.setDataAbertura(votacaoEntity.getDataAbertura());
        votacaoDTO.setId(votacaoEntity.getId());
        apiResponseVotacaoDTO.setVotacao(votacaoDTO);

        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId(1L);
        apiResponseAssociadoDTO.setAssociado(associadoDTO);
        Mockito.when(votacaoService.getVotacaoById(1L)).thenReturn(apiResponseVotacaoDTO);
        Mockito.when(associadoService.getAssociadoById(1L)).thenReturn(apiResponseAssociadoDTO);
        associadoDTO.setCpf("87280549039");
        String responseApi = "ABLE_TO_VOTE";
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(LocalDateTime.now());
        Mockito.when(apiCpfService.verificaCpf(associadoDTO.getCpf())).thenReturn(responseApi);
        Mockito.when(votoRepository.findByAssociadoEntityAndVotacaoEntity(
                votarEntity.getAssociadoEntity(),votarEntity.getVotacaoEntity())).thenReturn(Optional.of(votarEntity));
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotacaoNaoEstaDisponivel(){

        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco votacao
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setId(1L);
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setPauta(pautaEntity);
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
