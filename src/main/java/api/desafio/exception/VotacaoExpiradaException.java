package api.desafio.exception;

public class VotacaoExpiradaException extends RuntimeException{
    public VotacaoExpiradaException(String message) {
        super(message);
    }

    public VotacaoExpiradaException(String message, Throwable cause){
        super(message, cause);
    }
}
