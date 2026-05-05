package com.aleksander.wordgames.findword.exception;

import org.springframework.http.HttpStatus;

import com.aleksander.wordgames.exception.ApiException;

public class FindWordException extends ApiException {
    public FindWordException(String message, HttpStatus status) {
        super(message, status);
    }
}
