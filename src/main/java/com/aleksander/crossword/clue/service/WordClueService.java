package com.aleksander.crossword.clue.service;

import java.util.Optional;

import com.aleksander.crossword.clue.model.WordClue;

public interface WordClueService {
    Optional<WordClue> getClue(String lemma);
}