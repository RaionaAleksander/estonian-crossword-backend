package com.aleksander.wordgames.savedgame.exception;

import org.springframework.http.HttpStatus;

import com.aleksander.wordgames.exception.ApiException;

public class SavedGameException extends ApiException {
    public SavedGameException(String message, HttpStatus status) {
        super(message, status);
    }
}