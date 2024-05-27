package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorDealer {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> dealEntityNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> MethodArgumentNotValid(MethodArgumentNotValidException e){
        List<FieldError> errors = e.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ValidationError::new).toList());
    }

    private record ValidationError(String field, String message){
        public ValidationError (FieldError e){
            this(e.getField(), e.getDefaultMessage());
        }
    }

}
