package com.aleksander.wordgames.word.exception;

import org.springframework.http.HttpStatus;

import com.aleksander.wordgames.exception.ApiException;

public class WordNotFoundException extends ApiException {
    public WordNotFoundException(String word) {
        super("Word not found: " + word, HttpStatus.NOT_FOUND);
    }
}