package com.aleksander.wordgames.findword.exception;

import org.springframework.http.HttpStatus;

public class FindWordValidationException extends FindWordException {
    public FindWordValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}