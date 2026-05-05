package com.aleksander.wordgames.wordsearch.exception;

import org.springframework.http.HttpStatus;

public class WordSearchValidationException extends WordSearchException {
    public WordSearchValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}