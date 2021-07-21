package api.desafio.domain.services.apiCpf;

import api.desafio.domain.dto.apiCpf.ApiCpfDTO;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiCpfService {
    private String urlCpf;
    @Value("${apicpf.url}")
    public void seturlCpf(String apicpf){
        this.urlCpf = apicpf;
    }




    public ApiCpfDTO verificaCpf(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        String url = urlCpf+cpf;
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(urlCpf);
        ResponseEntity<ApiCpfDTO> responseCPF = restTemplate.getForEntity(url, ApiCpfDTO.class);

        if(responseCPF.getStatusCodeValue() == 404){
            throw new APIException(APIExceptionEnum.CPF_INVALIDO);
        }
        ApiCpfDTO apiCPF = responseCPF.getBody();
        return apiCPF;
        /*RestTemplate restTemplate = new RestTemplate();
        String url = "https://user-info.herokuapp.com/users/" + "85980439072";
        ResponseEntity<ApiCpfDTO> responseCPF = restTemplate.getForEntity(url, ApiCpfDTO.class);
        ApiCpfDTO apiCPF = responseCPF.getBody();
        System.out.println(apiCPF.getStatus());*/
    }
}
