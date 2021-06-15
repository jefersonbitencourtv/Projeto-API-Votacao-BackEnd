package api.desafio.exception;

public class AssociadoJaVotouException extends RuntimeException{

    public AssociadoJaVotouException(String message) {
        super(message);
    }

    public AssociadoJaVotouException(String message, Throwable cause){
        super(message, cause);
    }
}
