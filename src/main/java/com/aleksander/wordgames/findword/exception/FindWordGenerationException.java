package com.aleksander.wordgames.findword.exception;

import org.springframework.http.HttpStatus;

public class FindWordGenerationException extends FindWordException {
    public FindWordGenerationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
