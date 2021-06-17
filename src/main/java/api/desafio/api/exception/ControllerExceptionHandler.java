package api.desafio.api.exception;

import api.desafio.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({InvalidFormatException.class})
    public ResponseEntity<StandardError> campoFormatoInvalido(HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Formato inválido", "Digite um formato válido");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjetoNaoEncontradoException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(JaExisteVotacaoParaAPautaException.class)
    public ResponseEntity<StandardError> jaExisteVotacaoParaAPautaException(JaExisteVotacaoParaAPautaException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "Ja existe votação para a pauta", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(AssociadoJaVotouException.class)
    public ResponseEntity<StandardError> associadoJaVotou(AssociadoJaVotouException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "Associado ja votou", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(VotacaoExpiradaException.class)
    public ResponseEntity<StandardError> votacaoExpiradaException(VotacaoExpiradaException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "Votação expirada", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<StandardError> campoInvalidoException(CampoInvalidoException e, HttpServletRequest request) {
        var error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Formato invalido", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationNotNullNotEmpty(MethodArgumentNotValidException e, HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "Formato invalido", e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(CPFInvalidoException.class)
    public ResponseEntity<StandardError> CPFInvalidoException(CPFInvalidoException e, HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), HttpStatus.CONFLICT.value(), "CPF invalido", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<StandardError> APIException(APIException e, HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), e.apiExceptionEnum.getStatus().value(), e.apiExceptionEnum.getErro(), e.getMessage());
        return ResponseEntity.status(e.apiExceptionEnum.getStatus()).body(error);

    }
}


