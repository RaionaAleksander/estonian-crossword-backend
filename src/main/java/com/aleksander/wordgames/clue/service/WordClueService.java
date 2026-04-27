package com.aleksander.wordgames.clue.service;

import java.util.Optional;

import com.aleksander.wordgames.clue.model.WordClue;

public interface WordClueService {
    Optional<WordClue> getClue(String lemma);
}