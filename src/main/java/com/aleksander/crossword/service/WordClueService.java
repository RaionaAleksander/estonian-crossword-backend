package com.aleksander.crossword.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.aleksander.crossword.clue.WordClueProvider;

@Service
@RequiredArgsConstructor
public class WordClueService {

    private final WordClueProvider provider;

    public String getClue(String lemma) {
        return provider.getClue(lemma)
                .orElse("No definition available");
    }
}