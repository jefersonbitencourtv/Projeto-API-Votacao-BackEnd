package api.desafio.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
}
