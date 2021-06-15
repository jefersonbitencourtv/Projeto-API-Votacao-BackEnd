package api.desafio.exception;

public class CampoInvalidoException extends RuntimeException{

    public CampoInvalidoException(String message) {
        super(message);
    }

    public CampoInvalidoException(String message, Throwable cause){
        super(message, cause);
    }
}
