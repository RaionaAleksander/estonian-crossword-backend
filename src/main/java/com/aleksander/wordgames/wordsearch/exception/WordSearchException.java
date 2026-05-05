package com.aleksander.wordgames.wordsearch.exception;

import org.springframework.http.HttpStatus;

import com.aleksander.wordgames.exception.ApiException;

public class WordSearchException extends ApiException {
    public WordSearchException(String message, HttpStatus status) {
        super(message, status);
    }
}