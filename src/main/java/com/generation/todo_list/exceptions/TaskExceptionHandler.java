package com.generation.todo_list.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE) // For exceptions, TaskExceptionHandler takes precedence
@ControllerAdvice // Addresses exception across the entire application (globally)
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

    // As TaskExceptionHandler is the highest precedence, it will be called first
    // If any other exception is thrown, it will be passed to the next exception handler

    // When user sends data that is not readable, handleHttpMessageNotReadable returns a message with a BAD_REQUEST status code
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //Instantiate MessageNotReadableException
        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        // Create a new Hashmap for errorResponse
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", messageNotReadableException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // When a resource that the user is looking for is not found, Provide feedback
    // By customizing the exception handler to inform the user that the resource looked for is not found
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(ResourceNotFoundException ex) {

        // Create a new Hashmap for errorResponse
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    // When the arguments sent in to the request body does not comply with the rules in the entity model
    // This exception handler will be triggered (detecks @Valid requestbody)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Create a new Hashmap for errorResponse
        Map<String, String> errors = new HashMap<>();

        // Loop through errors and add them to the Hashmap (errors)
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            errors.put(field, message);
        });

        // Create an object because it is a series of erros
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
