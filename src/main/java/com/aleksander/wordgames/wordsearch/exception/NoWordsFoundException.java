package com.aleksander.wordgames.wordsearch.exception;

public class NoWordsFoundException extends WordSearchException {
    public NoWordsFoundException() {
        super("No words found for given filters");
    }
}