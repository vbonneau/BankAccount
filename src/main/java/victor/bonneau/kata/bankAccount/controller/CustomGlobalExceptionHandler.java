package victor.bonneau.kata.bankAccount.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import victor.bonneau.kata.bankAccount.exception.ObjectNotFoundException;

	
@RestControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(ObjectNotFoundException e) {

        return ResponseEntity.notFound().build();

    }
}
