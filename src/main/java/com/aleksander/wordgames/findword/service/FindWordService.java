package com.aleksander.wordgames.findword.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.aleksander.wordgames.findword.exception.FindWordValidationException;
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

        if (!wordService.exists(mainWord)) {
            throw new FindWordValidationException("Main word not found in dictionary: " + mainWord);
        }

        int gridSize = request.getMaxCrossLength();
        int mainWordGridIndex = request.getMainWordGridIndex() != null
                ? request.getMainWordGridIndex()
                : request.getMaxCrossLength() / 2;

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
                        usedWords,
                        request.getFilter());

                if (clue == null) {
                    success = false;
                    break;
                }

                clues.add(clue);
                usedWords.add(clue.getWord());
            }

            if (success) {

                Direction direction = request.getMainWordDirection() != null
                        ? request.getMainWordDirection()
                        : (random.nextBoolean() ? Direction.DOWN : Direction.RIGHT);

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
            Set<String> usedWords,
            WordFilterRequest baseFilter) {

        WordFilterRequest filter = new WordFilterRequest();

        if (baseFilter != null) {
            filter.setMinLength(baseFilter.getMinLength());
            filter.setStartsWith(baseFilter.getStartsWith());
            filter.setEndsWith(baseFilter.getEndsWith());
            filter.setNotContains(baseFilter.getNotContains());
            filter.setPattern(baseFilter.getPattern());
        }

        Set<String> contains = new LinkedHashSet<>();

        if (baseFilter != null && baseFilter.getContains() != null) {
            contains.addAll(baseFilter.getContains());
        }

        contains.add(String.valueOf(letter));

        filter.setContains(new ArrayList<>(contains));

        Integer userMax = baseFilter != null ? baseFilter.getMaxLength() : null;

        if (userMax == null) {
            filter.setMaxLength(gridSize);
        } else {
            filter.setMaxLength(Math.min(userMax, gridSize));
        }

        Set<String> excluded = new HashSet<>();

        // 1. user excluded words
        if (baseFilter != null && baseFilter.getExcludedWords() != null) {
            excluded.addAll(baseFilter.getExcludedWords());
        }

        // 2. game used words
        excluded.addAll(usedWords);

        filter.setExcludedWords(new ArrayList<>(excluded));

        filter.setLimit(20);
        filter.setRandom(true);

        List<WordDto> candidates = wordService.getWords(filter).getWords();

        if (candidates.isEmpty()) {
            return null;
        }

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
