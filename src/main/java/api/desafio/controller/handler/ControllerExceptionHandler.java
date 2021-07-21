package api.desafio.controller.handler;

import api.desafio.exception.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        var error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Formato inválido", "Digite um formato válido");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationNotNullNotEmpty(MethodArgumentNotValidException e, HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Formato invalido", e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<StandardError> APIException(APIException e, HttpServletRequest request){
        var error = new StandardError(LocalDateTime.now(), e.apiExceptionEnum.getStatus().value(), e.apiExceptionEnum.getErro(), e.getMessage());
        return ResponseEntity.status(e.apiExceptionEnum.getStatus()).body(error);

    }
}


