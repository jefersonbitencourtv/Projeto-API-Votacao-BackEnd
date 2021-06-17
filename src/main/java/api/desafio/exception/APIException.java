package api.desafio.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class APIException extends RuntimeException{

    public APIExceptionEnum apiExceptionEnum;

    public APIException(APIExceptionEnum apiExceptionEnum) {
        super(apiExceptionEnum.getTexto());
        this.apiExceptionEnum = apiExceptionEnum;
    }
}
