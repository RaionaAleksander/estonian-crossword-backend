package com.aleksander.wordgames.wordsearch.exception;

import org.springframework.http.HttpStatus;

public class NoWordsFoundException extends WordSearchException {
    public NoWordsFoundException() {
        super("No words found for given filters", HttpStatus.NOT_FOUND);
    }
}