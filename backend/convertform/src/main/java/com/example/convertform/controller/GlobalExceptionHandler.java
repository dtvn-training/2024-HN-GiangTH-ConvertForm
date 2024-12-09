package com.example.convertform.controller;

import com.gh.mygreen.xlsmapper.SheetNotFoundException;
import com.gh.mygreen.xlsmapper.cellconverter.TypeBindException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TypeBindException.class)
    public ResponseEntity<String> handleTypeBind(TypeBindException e) {
        logger.trace(e.toString());
        String msg = e.getMessage();
        int indexClass1 = msg.lastIndexOf('.');
        int indexClass2 = msg.indexOf('#');
        int index1 = e.getMessage().indexOf('(');
        int index2 = e.getMessage().indexOf(')');
        return ResponseEntity.status(500).body(msg.substring(35, indexClass2) + " - " + msg.substring(index1 + 1, index2));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleIO(ValidationException e) {
        logger.trace(e.toString());
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> handleParse(ValidationException e) {
        logger.trace(e.toString());
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handle(UsernameNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethod(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errMessage = err.getDefaultMessage();
            errors.put(fieldName, errMessage);
        });

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SheetNotFoundException.class)
    public String handleInputFormatException(SheetNotFoundException ex) {
        return "Sheet not found. Please choose right format of input file!";
    }
}
