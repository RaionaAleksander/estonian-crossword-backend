package com.aleksander.wordgames.wordsearch.exception;

import org.springframework.http.HttpStatus;

public class WordSearchGenerationException extends WordSearchException {
    public WordSearchGenerationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}