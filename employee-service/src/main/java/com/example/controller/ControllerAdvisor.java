package com.example.controller;

import com.example.dto.ErrorResponseDTO;
import com.example.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public final ResponseEntity<ErrorResponseDTO> handleDataNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponseDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<ErrorResponseDTO> handleException(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
