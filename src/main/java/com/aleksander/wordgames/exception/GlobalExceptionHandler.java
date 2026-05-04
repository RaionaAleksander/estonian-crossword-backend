package com.aleksander.wordgames.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aleksander.wordgames.findword.exception.FindWordException;
import com.aleksander.wordgames.word.exception.InvalidSortException;
import com.aleksander.wordgames.word.exception.WordNotFoundException;
import com.aleksander.wordgames.wordsearch.exception.WordSearchException;

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

    @ExceptionHandler(FindWordException.class)
    public ResponseEntity<String> handleFindWordException(FindWordException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<String> handleWordNotFound(WordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}