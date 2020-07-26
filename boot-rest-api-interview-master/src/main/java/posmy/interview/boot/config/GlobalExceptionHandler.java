package posmy.interview.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;;

@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity handleBindingErrors(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
