package com.aleksander.wordgames.findword.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aleksander.wordgames.clue.model.WordClue;
import com.aleksander.wordgames.clue.service.WordClueService;
import com.aleksander.wordgames.common.enums.Direction;
import com.aleksander.wordgames.findword.dto.FindWordClueDto;
import com.aleksander.wordgames.findword.dto.FindWordRequest;
import com.aleksander.wordgames.findword.dto.FindWordResponse;
import com.aleksander.wordgames.findword.exception.FindWordGenerationException;
import com.aleksander.wordgames.findword.validation.FindWordValidator;
import com.aleksander.wordgames.generator.GameGenerator;
import com.aleksander.wordgames.word.dto.WordDto;
import com.aleksander.wordgames.word.dto.WordFilterRequest;
import com.aleksander.wordgames.word.service.WordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindWordService implements GameGenerator<FindWordRequest, FindWordResponse> {

    private final WordService wordService;
    private final WordClueService wordClueService;
    private final Random random = new Random();

    @Override
    public FindWordResponse generate(FindWordRequest request) {
        FindWordValidator.validate(request);
        String mainWord = request.getMainWord().trim().toLowerCase();

        int gridSize = request.getMaxCrossLength();
        int mainWordGridIndex = gridSize / 2;

        for (int attempt = 0; attempt < 100; attempt++) {
            boolean success = true;
            List<FindWordClueDto> clues = new ArrayList<>();
            Set<String> usedWords = new HashSet<>();
            usedWords.add(mainWord);

            for (int i = 0; i < mainWord.length(); i++) {

                char letter = mainWord.charAt(i);

                FindWordClueDto clue = findWordForLetter(
                        letter,
                        i,
                        mainWordGridIndex,
                        gridSize,
                        usedWords);

                if (clue == null) {
                    success = false;
                    break;
                }

                clues.add(clue);
                usedWords.add(clue.getWord());
            }

            if (success) {

                Direction direction = pickRandomDirection();

                List<FindWordClueDto> enrichedClues = enrichClues(clues);

                return new FindWordResponse(
                        mainWord,
                        direction,
                        mainWordGridIndex,
                        request.getMaxCrossLength(),
                        enrichedClues,
                        Instant.now());
            }

        }

        throw new FindWordGenerationException("Failed to generate find-word puzzle");
    }

    private FindWordClueDto findWordForLetter(
            char letter,
            int mainWordLetterIndex,
            int mainWordGridIndex,
            int gridSize,
            Set<String> usedWords) {

        WordFilterRequest filter = new WordFilterRequest();
        filter.setContains(List.of(String.valueOf(letter)));
        filter.setExcludedWords(new ArrayList<>(usedWords));
        filter.setMaxLength(gridSize);
        filter.setLimit(20);
        filter.setRandom(true);

        List<WordDto> candidates = wordService.getWords(filter).getWords();

        if (candidates.isEmpty()) {
            return null;
        }

        Collections.shuffle(candidates, random);

        for (WordDto dto : candidates) {

            String word = dto.getLemma();

            List<Integer> validIndexes = findValidIndexes(word, letter, mainWordGridIndex, gridSize);

            if (validIndexes.isEmpty()) {
                continue;
            }

            int wordIndex = validIndexes.get(random.nextInt(validIndexes.size()));

            return new FindWordClueDto(
                    mainWordLetterIndex,
                    wordIndex,
                    word,
                    null);
        }

        return null;
    }

    private List<Integer> findValidIndexes(String word, char letter, int mainWordGridIndex, int gridSize) {

        List<Integer> validIndexes = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {

            if (word.charAt(i) != letter) {
                continue;
            }

            int start = mainWordGridIndex - i;
            int end = start + word.length() - 1;

            if (start >= 0 && end < gridSize) {
                validIndexes.add(i);
            }
        }

        return validIndexes;
    }

    private Direction pickRandomDirection() {
        return random.nextBoolean()
                ? Direction.RIGHT
                : Direction.DOWN;
    }

    private List<FindWordClueDto> enrichClues(List<FindWordClueDto> clues) {

        List<FindWordClueDto> result = new ArrayList<>();

        for (FindWordClueDto clueDto : clues) {

            String definition = wordClueService.getClue(clueDto.getWord())
                    .map(WordClue::definition)
                    .orElse("Puudub vihje");

            result.add(new FindWordClueDto(
                    clueDto.getMainWordIndex(),
                    clueDto.getWordIndex(),
                    clueDto.getWord(),
                    definition));
        }

        return result;
    }
}
