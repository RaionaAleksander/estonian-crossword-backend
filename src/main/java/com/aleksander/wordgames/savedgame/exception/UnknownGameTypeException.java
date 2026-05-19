package com.aleksander.wordgames.savedgame.exception;

import org.springframework.http.HttpStatus;

public class UnknownGameTypeException extends SavedGameException {
    public UnknownGameTypeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}