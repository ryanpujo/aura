package com.praim.inventory.common.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDetails> handleNotFoundException(Exception ex, WebRequest req) {
    var errDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));
    return new ResponseEntity<>(errDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorDetails> handleDataIntegrityException(Exception ex, WebRequest req) {
    var errDetails = new ErrorDetails(
      LocalDateTime.now(), 
      ex.getMessage(), 
      req.getDescription(false)
    );
    return new ResponseEntity<>(errDetails, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest req) {
    var errDetails = new ErrorDetails(
      LocalDateTime.now(), 
      ex.getMessage(), 
      req.getDescription(false)
    );
    return new ResponseEntity<>(errDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
