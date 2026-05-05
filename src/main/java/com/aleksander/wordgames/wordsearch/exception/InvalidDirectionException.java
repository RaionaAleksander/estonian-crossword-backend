package com.aleksander.wordgames.wordsearch.exception;

import org.springframework.http.HttpStatus;

public class InvalidDirectionException extends WordSearchException {
    public InvalidDirectionException(String dir) {
        super("Unexpected direction: " + dir, HttpStatus.BAD_REQUEST);
    }
}