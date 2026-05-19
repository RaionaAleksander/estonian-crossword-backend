package com.aleksander.wordgames.savedgame.exception;

import org.springframework.http.HttpStatus;

public class SavedGameParseException extends SavedGameException {
    public SavedGameParseException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}