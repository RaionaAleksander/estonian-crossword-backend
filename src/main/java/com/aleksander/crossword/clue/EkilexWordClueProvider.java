package com.aleksander.crossword.clue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EkilexWordClueProvider implements WordClueProvider {

    @Override
    public Optional<String> getClue(String lemma) {
        return Optional.empty();
    }
}