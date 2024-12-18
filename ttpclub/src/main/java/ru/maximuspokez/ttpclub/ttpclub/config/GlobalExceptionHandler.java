package ru.maximuspokez.ttpclub.ttpclub.config;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Duplicate key error: " + ex.getMostSpecificCause().getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Error: " + ex.getMessage());
  }
}