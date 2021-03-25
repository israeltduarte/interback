package br.com.iser.interback.error;

import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.exception.EncryptingException;
import br.com.iser.interback.exception.ValidationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String ISRAEL_DUARTE = "ISRAEL DUARTE";

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> customCustomerNotFound(Exception e) {

    CustomErrorResponse errors = new CustomErrorResponse();

    errors.setError(e.getMessage());
    errors.setStatus(HttpStatus.NOT_FOUND.value());
    errors.setOwner(ISRAEL_DUARTE);

    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationErrorException.class)
  public ResponseEntity<CustomErrorResponse> customValidation(Exception e) {

    CustomErrorResponse errors = new CustomErrorResponse();

    errors.setError(e.getMessage());
    errors.setStatus(HttpStatus.BAD_REQUEST.value());
    errors.setOwner(ISRAEL_DUARTE);

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EncryptingException.class)
  public ResponseEntity<CustomErrorResponse> customEncrypting(Exception e) {

    CustomErrorResponse errors = new CustomErrorResponse();

    errors.setError(e.getMessage());
    errors.setStatus(HttpStatus.BAD_REQUEST.value());
    errors.setOwner(ISRAEL_DUARTE);

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}