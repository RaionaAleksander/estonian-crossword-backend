package com.aleksander.crossword.clue.selector;

import java.util.List;

import com.aleksander.crossword.clue.model.WordClue;
import com.aleksander.crossword.clue.model.WordMeaning;

public interface ClueSelector {
    WordClue select(List<WordMeaning> meanings);
}