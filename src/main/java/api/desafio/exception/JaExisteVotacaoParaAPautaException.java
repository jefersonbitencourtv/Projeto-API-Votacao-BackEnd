package api.desafio.exception;

public class JaExisteVotacaoParaAPautaException extends RuntimeException{
    public JaExisteVotacaoParaAPautaException(String message) {
        super(message);
    }

    public JaExisteVotacaoParaAPautaException(String message, Throwable cause){
        super(message, cause);
    }
}
