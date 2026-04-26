package com.aleksander.crossword.clue;

import java.util.Optional;

public interface WordClueProvider {
    Optional<String> getClue(String lemma);
}