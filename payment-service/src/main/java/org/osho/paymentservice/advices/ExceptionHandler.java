package org.osho.paymentservice.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException() {
        System.out.println("Something went Wrong");
        return new ResponseEntity<>("Some Error Occurred While Payment Please Retry Again", HttpStatus.NOT_FOUND);
    }
}

