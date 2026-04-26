package com.aleksander.crossword.exception.wordclue;

public class WordClueNotFoundException extends RuntimeException {
    public WordClueNotFoundException(String word) {
        super("No clue found for word: " + word);
    }
}