package api.desafio;

import api.desafio.domain.repository.AssociadoRepository;
import api.desafio.domain.repository.ResultadoRepository;
import api.desafio.domain.repository.VotacaoRepository;
import api.desafio.domain.repository.VotoRepository;
import api.desafio.domain.services.AssociadoService;
import api.desafio.domain.services.ResultadoService;
import api.desafio.domain.services.VotacaoService;
import api.desafio.domain.services.VotoService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private ResultadoRepository resultadoRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @InjectMocks
    private VotoService votarService;

   /* @Test
    public void testInserirVoto(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
        votacaoEntity.setDuracaoVotacao(2000L);
        votacaoEntity.setDataAbertura(dataAbertura);
        Mockito.when(votacaoRepository.findById(1L)).thenReturn(Optional.of(votacaoEntity));
        Mockito.when(votacaoService.getDataAberturaUsoValidacaoInserirVoto(1L)).thenReturn(dataAbertura);
        Mockito.when(votacaoService.getDuracaoVotacaoUsoValidacaoInserirVoto(1L)).thenReturn(2000L);
        //Objeto do banco resultado
        Mockito.doNothing().when(resultadoService).inserirVotoNoResultado("SIM",1L);

        //Objeto do banco votar
        VotoEntity votarEntityEntrada = new VotoEntity();
        votarEntityEntrada.setVoto("SIM");
        votarEntityEntrada.setIdVotacao(1L);
        votarEntityEntrada.setIdAssociado(1L);

        VotoEntity votarEntityRetorno = new VotoEntity();
        votarEntityRetorno.setVoto("SIM");
        votarEntityRetorno.setIdVotacao(1L);
        votarEntityRetorno.setIdAssociado(1L);
        votarEntityRetorno.setId(1L);

        Mockito.when(votoRepository.save(votarEntityEntrada)).thenReturn(votarEntityRetorno);

        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("SIM");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        ApiResponse responsePadraoService = votarService.inserirVoto(votarRequest);

        //Objeto de teste
        VotoDTO votarDTO = new VotoDTO();
        votarDTO.setVoto("SIM");
        votarDTO.setIdVotacao(1L);
        votarDTO.setIdAssociado(1);
        votarDTO.setId(1L);
        ApiResponse responsePadraoTeste = new ApiResponse();
        responsePadraoTeste.setObjeto(votarDTO);
        responsePadraoTeste.setTexto("Voto criado com sucesso");

        Assert.assertEquals(responsePadraoService,responsePadraoTeste);

    }

    @Test
    public void testGetVotos(){
        //Cria objeto do banco
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L)        ;
        List<VotoEntity> listaVotarEntity = new ArrayList<>();
        listaVotarEntity.add(votarEntity);
        Mockito.when(votoRepository.findAll()).thenReturn(listaVotarEntity);
        ApiResponse responsePadraoService = votarService.getVoto();

        //OBjeto para teste
        ApiResponse responsePadraoTeste = new ApiResponse();
        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"sim");
        List<VotoDTO> listaResultadoDTO = new ArrayList<>();
        listaResultadoDTO.add(votarDTO);
        responsePadraoTeste.setListaObjeto(Arrays.asList(listaResultadoDTO.toArray()));
        Assert.assertEquals(responsePadraoService,responsePadraoTeste);
    }

    @Test
    public void testGetVotosById(){
        //Cria objeto
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setId(1L);
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L);

        Mockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(votarEntity));

        ApiResponse responsePadraoService = votarService.getVotoById(1L);

        ApiResponse responsePadraoTeste = new ApiResponse();

        VotoDTO votarDTO = new VotoDTO(1L,1L,1L,"sim");
        responsePadraoTeste.setObjeto(votarDTO);
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
        //Votar entitity é chamada de outro metdoWW
        VotarRequest votarRequest = new VotarRequest();
        votarRequest.setVoto("sim");
        votarRequest.setIdVotacao(1L);
        votarRequest.setIdAssociado(1L);
        VotoEntity votarEntity = new VotoEntity();
        votarEntity.setVoto("sim");
        votarEntity.setIdVotacao(1L);
        votarEntity.setIdAssociado(1L);
        votarEntity.setId(1L);
        Mockito.when(votoRepository.findByIdAssociadoAndIdVotacao(
                votarEntity.getIdAssociado(),votarEntity.getIdVotacao())).thenReturn(Optional.of(votarEntity));
        Mockito.when(votarService.inserirVoto(votarRequest)).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVotacaoNaoEstaDisponivel(){
        LocalDateTime dataAbertura = LocalDateTime.now();
        //Objeto do banco votacao
        VotacaoEntity votacaoEntity = new VotacaoEntity();
        votacaoEntity.setId(1L);
        votacaoEntity.setIdPauta(1L);
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

*/

}
