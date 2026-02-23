package com.example.MyAPP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BhajanNotFoundException.class)
    public ResponseEntity<ErrorResponse> BhajanNotFoundException(BhajanNotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> UsernameNotFoundException(UsernameNotFoundException e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> GeneralException(Exception e){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
