package com.aleksander.wordgames.clue.exception;

public class WordClueNotFoundException extends RuntimeException {
    public WordClueNotFoundException(String word) {
        super("No clue found for word: " + word);
    }
}