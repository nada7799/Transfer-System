package com.TransferApp.MoneyTransfer.exception;


import com.TransferApp.MoneyTransfer.exception.responses.ErrorDetails;
import com.TransferApp.MoneyTransfer.exception.responses.ValidationFailedResponse;
import com.TransferApp.MoneyTransfer.exception.responses.ViolationErrors;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class TransactionsAppExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<Object> customerAlreadyExistsExceptionHandling(CustomerNotFoundException exception, WebRequest webRequest){
        return new ResponseEntity<>(ErrorDetails.builder()
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .httpStatus(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomerAlreadyExistsException.class})
    public ResponseEntity<Object> customerAlreadyExistsExceptionHandling(CustomerAlreadyExistsException exception, WebRequest webRequest){
        return new ResponseEntity<>(ErrorDetails.builder()
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .httpStatus(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> validationFailedExceptionHandling(MethodArgumentNotValidException exception, WebRequest webRequest){
        ValidationFailedResponse error = ValidationFailedResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        for(FieldError  fieldError : exception.getBindingResult().getFieldErrors()){
            error.getViolations().add(new ViolationErrors(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationExceptionHandling(ConstraintViolationException exception, WebRequest webRequest){
        ValidationFailedResponse error = ValidationFailedResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        for(ConstraintViolation<?> violation : exception.getConstraintViolations()){
            error.getViolations().add(new ViolationErrors(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

  @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(ErrorDetails.builder()
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.badRequest().body("Invalid input format: " + ex.getMessage());
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleAuthenticationException(ExpiredJwtException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
