package api.desafio.domain.services.apiCpf;

import api.desafio.cliente.ApiCpfCliente;
import api.desafio.domain.dto.apiCpf.ApiCpfDTO;
import api.desafio.exception.APIException;
import api.desafio.exception.APIExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ApiCpfService {
    private final ApiCpfCliente apiCpfCliente;
    private final ObjectMapper mapper;

    public ApiCpfService(ApiCpfCliente apiCpfCliente, ObjectMapper mapper) {
        this.apiCpfCliente = apiCpfCliente;
        this.mapper = mapper;
    }

    public String verificaCpf(String cpf) {
        try {
            Response responseApi = apiCpfCliente.validaCpf(cpf);
            if (responseApi.status() == 404) {
                throw new APIException(APIExceptionEnum.CPF_INVALIDO);
            }
            String bodyStr = Util.toString(responseApi.body().asReader(StandardCharsets.UTF_8));
            ApiCpfDTO apiCpfDTO = mapper.readValue(bodyStr, ApiCpfDTO.class);
            return apiCpfDTO.getStatus();
        } catch (JsonMappingException ex) {
            throw new APIException(APIExceptionEnum.ERRO_API);
        } catch (JsonProcessingException ex) {
            throw new APIException(APIExceptionEnum.ERRO_API);
        } catch (IOException ex) {
            throw new APIException(APIExceptionEnum.ERRO_API);
        }
    }
}

