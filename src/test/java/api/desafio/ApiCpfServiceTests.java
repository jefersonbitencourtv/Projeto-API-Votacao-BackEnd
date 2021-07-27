package api.desafio;

import api.desafio.cliente.ApiCpfCliente;
import api.desafio.domain.dto.apiCpf.ApiCpfDTO;
import api.desafio.domain.services.apiCpf.ApiCpfService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import io.swagger.annotations.Api;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

public class ApiCpfServiceTests {
    @Mock
    private ApiCpfCliente apiCpfCliente;
    @Mock
    private ObjectMapper mapper;
    @InjectMocks
    private ApiCpfService apiCpfService;

    /*@Test

    public void testVerificaCpf() throws IOException {
        Response response = Response.builder().status(200).build();
        Mockito.when(apiCpfCliente.validaCpf("87083162018")).thenReturn(response);
        ApiCpfDTO apiCpfDTO = new ApiCpfDTO();
        Mockito.when(mapper.readValue((JsonParser) response.body(), ApiCpfDTO.class)).thenReturn(apiCpfDTO);
        String string = (apiCpfService.verificaCpf("87083162018"));
        Assert.assertEquals(string, "ABLE_TO_VOTE");
    }*/


}
