package api.desafio;

import antlr.Utils;
import api.desafio.cliente.ApiCpfCliente;
import api.desafio.domain.dto.apiCpf.ApiCpfDTO;
import api.desafio.domain.entities.AssociadoEntity;
import api.desafio.domain.services.apiCpf.ApiCpfService;
import api.desafio.exception.APIException;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.Headers;
import feign.Request;
import feign.Response;
import feign.Util;
import io.swagger.annotations.Api;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiCpfServiceTests {
    @Mock
    private ApiCpfCliente apiCpfCliente;
    @Mock
    private ObjectMapper mapper;
    @Mock
    private Response response1;
    @Mock
    private Response.Body responseBody;
    @Mock
    private Utils utils;


    @InjectMocks
    @Spy
    private ApiCpfService apiCpfService;

    @Test
    public void testVerificaCpf() throws JsonProcessingException {
        Map<String, Collection<String>> headers = new HashMap<>();
        String status = "ABLE_TO_VOTE";

        Request request = Request.create(Request.HttpMethod.GET,"aa",headers, null,StandardCharsets.UTF_8);

        Response response = Response.builder().status(200).body(status, StandardCharsets.UTF_8).request(request).build();

        Mockito.when(apiCpfCliente.validaCpf(Mockito.any())).thenReturn(response);

        ApiCpfDTO apiCpfDTO = new ApiCpfDTO("ABLE_TO_VOTE");
        Mockito.doReturn(apiCpfDTO).when(mapper).readValue(status,ApiCpfDTO.class);
        //Mockito.when(mapper.readValue(status,ApiCpfDTO.class)).thenReturn(apiCpfDTO);

        String stringRetorno = apiCpfService.verificaCpf("231");
        Assert.assertEquals(status, stringRetorno);
    }
    @Test(expected = APIException.class)
    public void testVerificaCpf404(){
        Map<String, Collection<String>> headers = new HashMap<>();
        Request request = Request.create(Request.HttpMethod.GET,"aa",headers, (byte[]) null,StandardCharsets.UTF_8);

        String status = "ABLE_TO_VOTE";
        Response response = Response.builder().status(404).body(status, StandardCharsets.UTF_8).request(request).build();

        Mockito.when(apiCpfCliente.validaCpf(Mockito.any())).thenReturn(response);
        Mockito.when(apiCpfService.verificaCpf("12345678901")).thenThrow(APIException.class);
    }

    @Test(expected = APIException.class)
    public void testVerificaCpfCatchJsonMap() throws JsonProcessingException {
        Map<String, Collection<String>> headers = new HashMap<>();
        String status = "ABLE_TO_VOTE";

        Request request = Request.create(Request.HttpMethod.GET,"aa",headers, null,StandardCharsets.UTF_8);

        Response response = Response.builder().status(200).body(status, StandardCharsets.UTF_8).request(request).build();

        Mockito.when(apiCpfCliente.validaCpf(Mockito.any())).thenReturn(response);

        Mockito.doThrow(JsonMappingException.class).when(mapper).readValue(status,ApiCpfDTO.class);

        Mockito.when(apiCpfService.verificaCpf("87083162018")).thenThrow(JsonMappingException.class);

    }

    @Test(expected = APIException.class)
    public void testVerificaCpfCatchProcessingExce() throws JsonProcessingException {
        Map<String, Collection<String>> headers = new HashMap<>();
        String status = "ABLE_TO_VOTE";

        Request request = Request.create(Request.HttpMethod.GET,"aa",headers, null,StandardCharsets.UTF_8);

        Response response = Response.builder().status(200).body(status, StandardCharsets.UTF_8).request(request).build();

        Mockito.when(apiCpfCliente.validaCpf(Mockito.any())).thenReturn(response);

        Mockito.doThrow(JsonProcessingException.class).when(mapper).readValue(status,ApiCpfDTO.class);

        Mockito.when(apiCpfService.verificaCpf("87083162018")).thenThrow(JsonProcessingException.class);

    }

    @Test(expected = APIException.class)
    public void testVerificaCpfCatchIOException() throws IOException {
        Map<String, Collection<String>> headers = new HashMap<>();
        String status = "ABLE_TO_VOTE";

        Request request = Request.create(Request.HttpMethod.GET,"aa",headers, null,StandardCharsets.UTF_8);

        Response response = Response.builder().status(200).body(status, StandardCharsets.UTF_8).request(request).build();

        Mockito.when(apiCpfCliente.validaCpf(Mockito.any())).thenReturn(response);

        //Mockito.when(response1.asReader(StandardCharsets.UTF_8)).thenThrow(IOException.class);
        Mockito.doThrow(IOException.class).when(responseBody).asReader();
        Mockito.doThrow(IOException.class).when(response1).body().asReader(StandardCharsets.UTF_8);
        //Mockito.doThrow(IOException.class).when(response1).asReader();
        //Mockito.doThrow(new IOException()).when(apiCpfService).verificaCpf("1234");
        Mockito.when(apiCpfService.verificaCpf("87083162018")).thenThrow(IOException.class);

    }
}
