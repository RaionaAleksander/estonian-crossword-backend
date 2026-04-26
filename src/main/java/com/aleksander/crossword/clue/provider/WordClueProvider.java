package com.aleksander.crossword.clue.provider;

import java.util.List;

import com.aleksander.crossword.clue.model.WordMeaning;

public interface WordClueProvider {
    List<WordMeaning> getMeanings(String lemma);
}