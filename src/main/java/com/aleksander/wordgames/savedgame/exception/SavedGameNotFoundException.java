package com.aleksander.wordgames.savedgame.exception;

import org.springframework.http.HttpStatus;

public class SavedGameNotFoundException extends SavedGameException {
    public SavedGameNotFoundException(Long id) {
        super(
                "Saved game not found with id: " + id,
                HttpStatus.NOT_FOUND);
    }
}