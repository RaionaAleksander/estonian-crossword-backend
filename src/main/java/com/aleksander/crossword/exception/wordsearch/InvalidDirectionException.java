package com.aleksander.crossword.exception.wordsearch;

import com.aleksander.crossword.exception.WordSearchException;

public class InvalidDirectionException extends WordSearchException {
    public InvalidDirectionException(String dir) {
        super("Unexpected direction: " + dir);
    }
}