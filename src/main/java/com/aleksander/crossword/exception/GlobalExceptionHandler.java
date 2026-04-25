package com.aleksander.crossword.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSortException.class)
    public ResponseEntity<String> handleInvalidSort(InvalidSortException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(WordSearchException.class)
    public ResponseEntity<String> handleWordSearchException(WordSearchException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}