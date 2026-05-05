package com.aleksander.wordgames.word.exception;

import org.springframework.http.HttpStatus;

import com.aleksander.wordgames.exception.ApiException;

public class InvalidSortException extends ApiException {
    public InvalidSortException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}