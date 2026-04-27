package com.aleksander.wordgames.clue.selector;

import java.util.List;

import com.aleksander.wordgames.clue.model.WordClue;
import com.aleksander.wordgames.clue.model.WordMeaning;

public interface ClueSelector {
    WordClue select(List<WordMeaning> meanings);
}