package com.aleksander.wordgames.clue.provider;

import java.util.List;

import com.aleksander.wordgames.clue.model.WordMeaning;

public interface WordClueProvider {
    List<WordMeaning> getMeanings(String lemma);
}