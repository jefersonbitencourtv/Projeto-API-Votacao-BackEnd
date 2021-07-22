package api.desafio.domain.services.apiCpf;

import api.desafio.cliente.ApiCpfCliente;
import api.desafio.domain.dto.apiCpf.ApiCpfDTO;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import feign.Response;
import feign.Util;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ApiCpfService {
    //@Value("${apicpf.url}")
    //private String apiCpfUrl;
    private final ApiCpfCliente apiCpfCliente;

    public ApiCpfService(ApiCpfCliente apiCpfCliente) {
        this.apiCpfCliente = apiCpfCliente;
    }

    public ApiCpfDTO verificaCpf(String cpf) throws IOException {
        Response responseApi = apiCpfCliente.validaCpf(cpf);
        String bodyStr = Util.toString(responseApi.body().asReader(StandardCharsets.UTF_8));


        return null;
    }



        /*RestTemplate restTemplate = new RestTemplate();
        String url = apiCpfUrl+cpf;
        //ResponseEntity<ApiCpfDTO> responseCPF = restTemplate.getForEntity(url, ApiCpfDTO.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<ApiCpfDTO> responseCPF = restTemplate.exchange(url, HttpMethod.GET,entity,ApiCpfDTO.class);
        if(responseCPF.get == 404){
            throw new APIException(APIExceptionEnum.CPF_INVALIDO);
        }
        ApiCpfDTO apiCPF = responseCPF.getBody();
        return apiCPF;
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://user-info.herokuapp.com/users/" + "85980439072";
        ResponseEntity<ApiCpfDTO> responseCPF = restTemplate.getForEntity(url, ApiCpfDTO.class);
        ApiCpfDTO apiCPF = responseCPF.getBody();
        System.out.println(apiCPF.getStatus());*/
    }

