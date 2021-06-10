package api.desafio.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/desafio/")
public class IndexController {

    @GetMapping
    public String get() {
        return "API desafio tecnico";
    }
}
