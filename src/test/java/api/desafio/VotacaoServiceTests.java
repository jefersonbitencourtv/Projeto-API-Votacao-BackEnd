package api.desafio;

import api.desafio.domain.repository.PautaRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.services.PautaService;
import api.desafio.domain.services.VotacaoService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VotacaoServiceTests {
    @Mock
    private VotacaoRepository votacaoRepository;
    @Mock
    private PautaRepository pautaRepositorio;
    @InjectMocks
    private VotacaoService serviceVotacao;
    @Mock
    private PautaService servicePauta;

    /*@Test
    public void testInserirVotacao() {
        LocalDateTime DataAbertura = LocalDateTime.now();
        //Objeto da pauta
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(1L);
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("aa");
        pautaDTO.setDescricao("bb");
        pautaDTO.setId(1L);
        Mockito.when(pautaRepositorio.findById(1L)).thenReturn(Optional.of(pautaEntity));
        ApiResponse responsePadraoPauta = new ApiResponse();
        responsePadraoPauta.setObjeto(pautaDTO);
        Mockito.when(servicePauta.getPautaById(1L)).thenReturn(responsePadraoPauta);

        //Objeto para teste
        ApiResponse responsePadraoTeste = new ApiResponse();
        VotacaoDTO dto = new VotacaoDTO(1L,1L, 2000L, DataAbertura);
        responsePadraoTeste.setObjeto(dto);
        responsePadraoTeste.setTexto("Votacao criada com sucesso");

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(DataAbertura);
        Mockito.when(votacaoRepository.save(Mockito.any())).thenReturn(votacaoEntity);

        ApiResponse responsePadraoService;
        VotacaoRequest request = new VotacaoRequest();
        request.setIdPauta(1L);
        request.setDuracaoVotacao(2000L);
        responsePadraoService = serviceVotacao.inserirVotacao(request);
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
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
        PautaEntity pautaEntity = new PautaEntity();
        pautaEntity.setTitulo("aa");
        pautaEntity.setDescricao("bb");
        pautaEntity.setId(1L);
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("aa");
        pautaDTO.setDescricao("bb");
        pautaDTO.setId(1L);
        Mockito.when(pautaRepositorio.findById(1L)).thenReturn(Optional.of(pautaEntity));
        ApiResponse responsePadraoPauta = new ApiResponse();
        responsePadraoPauta.setObjeto(pautaDTO);
        Mockito.when(servicePauta.getPautaById(1L)).thenReturn(responsePadraoPauta);

        //Objeto da service
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(LocalDateTime.now());
        Mockito.when(votacaoRepository.findByIdPauta(1L)).thenReturn(Optional.of(votacaoEntity));
        serviceVotacao.getVotacaoByIdUsoValidacaoInsercaoResultado(1L);

        //Objeto para teste
        VotacaoRequest request = new VotacaoRequest();
        request.setIdPauta(1L);
        request.setDuracaoVotacao(2000L);


        Mockito.when(serviceVotacao.inserirVotacao(request)).thenThrow(APIException.class);
    }
    @Test
    public void testGetVotacao(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        List<VotacaoEntity> votacaoEntityList = new ArrayList<>();
        votacaoEntityList.add(votacaoEntity);
        Mockito.when(votacaoRepository.findAll()).thenReturn(votacaoEntityList);
        ApiResponse responsePadraoService;
        responsePadraoService = serviceVotacao.getVotacao();

        //Objeto para teste
        ApiResponse responsePadraoTeste = new ApiResponse();
        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L,dataAbertura);
        List<VotacaoDTO> listaVotacaoDTO = new ArrayList<>();
        listaVotacaoDTO.add(votacaoDTO);
        responsePadraoTeste.setListaObjeto(Arrays.asList(listaVotacaoDTO.toArray()));

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);
    }

    @Test
    public void testGetVotacaoById(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        ApiResponse responsePadraoService;
        responsePadraoService = serviceVotacao.getVotacaoById(1L);

        //Objeto para teste
        ApiResponse responsePadraoTeste = new ApiResponse();
        VotacaoDTO votacaoDTO = new VotacaoDTO(1L,1L,2000L,dataAbertura);
        responsePadraoTeste.setObjeto(votacaoDTO);

        Assert.assertEquals(responsePadraoService, responsePadraoTeste);

    }

    @Test
    public void testGetVotacaoByIdUsoValidacaoInsercaoResultado(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
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
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);

        Mockito.when(votacaoRepository.findByIdPauta(1L)).thenReturn(Optional.of(votacaoEntity));
        Optional<VotacaoEntity> optionalVotacaoEntityService= serviceVotacao.getVotacaoByIdPautaUsoValidacaoInserirVotacao(1L);

        //Objeto para teste
        Optional<VotacaoEntity> OptionalVotacaoEntityTeste = Optional.of(new VotacaoEntity(1L, 1L, 2000L, dataAbertura));

        Assert.assertEquals(optionalVotacaoEntityService, OptionalVotacaoEntityTeste);

    }
    @Test
    public void testGetDuracaoVotacaoUsoValidacaoInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Long duracaoVotacaoService = serviceVotacao.getDuracaoVotacaoUsoValidacaoInserirVoto(1L);

        //Objeto para teste
        VotacaoEntity votacaoEntityTeste = new VotacaoEntity(1L, 1L, 2000L, dataAbertura);

        Assert.assertEquals(duracaoVotacaoService, votacaoEntityTeste.getDuracaoVotacao());
    }

    @Test
    public void testGetDataAberturaUsoValidacaoInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        LocalDateTime dataAberturaService = serviceVotacao.getDataAberturaUsoValidacaoInserirVoto(1L);

        //Objeto para teste
        VotacaoEntity VotacaoEntityTeste = new VotacaoEntity(1L, 1L, 2000L, dataAbertura);

        Assert.assertEquals(dataAberturaService, VotacaoEntityTeste.getDataAbertura());
    }
*/
}
