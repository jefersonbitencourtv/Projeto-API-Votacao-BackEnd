package api.desafio.exception;

public class CPFInvalidoException extends RuntimeException{
    public CPFInvalidoException(String message) {
        super(message);
    }

    public CPFInvalidoException(String message, Throwable cause){
        super(message, cause);
    }
}

