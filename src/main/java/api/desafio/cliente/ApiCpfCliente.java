package api.desafio.cliente;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${apicpf.url}", name = "ApiCpfController")
public interface ApiCpfCliente {
    @GetMapping(path = "/{cpf}")
    Response validaCpf(@PathVariable("cpf") String cpf);
}
