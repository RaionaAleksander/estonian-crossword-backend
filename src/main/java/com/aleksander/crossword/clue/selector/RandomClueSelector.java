package com.aleksander.crossword.clue.selector;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.aleksander.crossword.clue.model.WordClue;
import com.aleksander.crossword.clue.model.WordMeaning;

@Component
public class RandomClueSelector implements ClueSelector {

    private final Random random = new Random();

    @Override
    public WordClue select(List<WordMeaning> meanings) {

        List<WordMeaning> nonEmpty = meanings.stream()
                .filter(m -> m.definitions() != null && !m.definitions().isEmpty())
                .toList();

        if (nonEmpty.isEmpty()) {
            return null;
        }

        WordMeaning meaning = nonEmpty.get(random.nextInt(nonEmpty.size()));
        String definition = meaning.definitions()
                .get(random.nextInt(meaning.definitions().size()));

        return new WordClue(meaning.meaningId(), definition);
    }
}