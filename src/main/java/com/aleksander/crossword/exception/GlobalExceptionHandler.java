package com.aleksander.crossword.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.exception.wordclue.WordClueFetchException;
import com.aleksander.crossword.exception.wordclue.WordClueNotFoundException;

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

    @ExceptionHandler(WordClueFetchException.class)
    public ResponseEntity<String> handleClueError(WordClueFetchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(WordClueNotFoundException.class)
    public ResponseEntity<String> handleClueNotFound(WordClueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}