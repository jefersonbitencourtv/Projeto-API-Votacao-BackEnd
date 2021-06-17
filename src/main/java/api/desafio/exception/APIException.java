package api.desafio.exception;

import lombok.Getter;
@Getter
public class APIException extends RuntimeException{

    public APIExceptionEnum apiExceptionEnum;

    public APIException(APIExceptionEnum apiExceptionEnum) {
        super(apiExceptionEnum.getTexto());
        this.apiExceptionEnum = apiExceptionEnum;
    }
}
