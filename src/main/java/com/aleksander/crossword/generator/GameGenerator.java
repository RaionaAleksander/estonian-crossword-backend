package com.aleksander.crossword.generator;

public interface GameGenerator<TRequest, TResponse> {
    TResponse generate(TRequest request);
}