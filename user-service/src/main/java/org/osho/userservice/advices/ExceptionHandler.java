package org.osho.userservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>("User Not Found For The Provided Id", HttpStatus.NOT_FOUND);
    }
}
