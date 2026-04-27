package com.aleksander.wordgames.wordsearch.exception;

public class InvalidDirectionException extends WordSearchException {
    public InvalidDirectionException(String dir) {
        super("Unexpected direction: " + dir);
    }
}