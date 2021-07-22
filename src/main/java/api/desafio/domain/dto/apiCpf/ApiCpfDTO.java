package api.desafio.domain.dto.apiCpf;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiCpfDTO implements Serializable {
    String status;
    String message;
    HttpStatus httpStatus;
}
