package com.aleksander.wordgames.generator;

public interface GameGenerator<TRequest, TResponse> {
    TResponse generate(TRequest request);
}