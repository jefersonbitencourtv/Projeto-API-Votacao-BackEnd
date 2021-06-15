package api.desafio.exception;

public class ObjetoNaoEncontradoException extends RuntimeException{
    public ObjetoNaoEncontradoException(String message) {
        super(message);
    }

    public ObjetoNaoEncontradoException(String message, Throwable cause){
        super(message, cause);
    }
}
