package com.aleksander.crossword.exception.wordsearch;

import com.aleksander.crossword.exception.WordSearchException;

public class NoWordsFoundException extends WordSearchException {
    public NoWordsFoundException() {
        super("No words found for given filters");
    }
}