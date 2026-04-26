package com.aleksander.crossword.clue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aleksander.crossword.clue.model.WordClue;
import com.aleksander.crossword.clue.model.WordMeaning;
import com.aleksander.crossword.clue.provider.WordClueProvider;
import com.aleksander.crossword.clue.selector.ClueSelector;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultWordClueService implements WordClueService {

    private final WordClueProvider provider;
    private final ClueSelector selector;

    @Override
    public Optional<WordClue> getClue(String lemma) {

        List<WordMeaning> meanings = provider.getMeanings(lemma);

        if (meanings.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(selector.select(meanings));
    }
}