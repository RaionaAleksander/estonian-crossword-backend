package com.aleksander.crossword.service;

import com.aleksander.crossword.dto.*;
import com.aleksander.crossword.exception.wordsearch.InvalidDirectionException;
import com.aleksander.crossword.exception.wordsearch.NoWordsFoundException;
import com.aleksander.crossword.exception.wordsearch.WordSearchGenerationException;
import com.aleksander.crossword.model.enums.Direction;
import com.aleksander.crossword.model.enums.SortOrder;
import com.aleksander.crossword.model.enums.SortType;
import com.aleksander.crossword.validation.WordSearchValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WordSearchService {

    private final WordService wordService;

    private final Random random = new Random();

    private static final char[] ESTONIAN_LETTERS = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'õ', 'ä', 'ö', 'ü'
    };

    public WordSearchResponse generate(WordSearchRequest request) {

        WordSearchValidator.validate(request);

        int rows = request.getRows();
        int cols = request.getCols();

        WordFilterRequest filter = new WordFilterRequest();
        filter.setLimit(request.getWordsCount());
        if (request.getMinLength() != null) {
            filter.setMinLength(request.getMinLength());
        }

        if (request.getMaxLength() != null) {
            filter.setMaxLength(request.getMaxLength());
        } else {
            filter.setMaxLength(Math.max(rows, cols));
        }

        // filter.setSort(SortType.LENGTH);
        // filter.setOrder(SortOrder.DESC);

        WordResponse wordResponse = wordService.getWords(filter);

        List<String> words = wordResponse.getWords()
                .stream()
                .map(WordDto::getLemma)
                .toList();

        if (words.isEmpty()) {
            throw new NoWordsFoundException();
        }

        for (int attempt = 0; attempt < 100; attempt++) {

            char[][] grid = new char[rows][cols];
            List<PlacementDto> placements = new ArrayList<>();

            boolean success = true;

            for (String word : words) {

                PlacementDto placement = tryPlaceWord(grid, word);

                if (placement == null) {
                    success = false;
                    break;
                }

                placements.add(placement);
            }

            if (success) {

                fillRandom(grid);

                return new WordSearchResponse(
                        grid,
                        words,
                        placements,
                        Instant.now());
            }
        }

        throw new WordSearchGenerationException("Failed to generate word search");
    }

    // ---------------- helpers ----------------

    private void fillRandom(char[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                if (grid[i][j] == '\u0000') {
                    grid[i][j] = ESTONIAN_LETTERS[random.nextInt(ESTONIAN_LETTERS.length)];
                }
            }
        }
    }

    private List<Direction> getValidDirections(String word, int rows, int cols) {

        List<Direction> directions = new ArrayList<>();

        // horizontal
        if (word.length() <= cols) {
            directions.add(Direction.RIGHT);
            directions.add(Direction.LEFT);
        }

        // vertical
        if (word.length() <= rows) {
            directions.add(Direction.DOWN);
            directions.add(Direction.UP);
        }

        return directions;
    }

    private PlacementDto tryPlaceWord(char[][] grid, String word) {

        int rows = grid.length;
        int cols = grid[0].length;

        List<Direction> validDirections = getValidDirections(word, rows, cols);

        if (validDirections.isEmpty()) {
            return null;
        }

        for (int attempt = 0; attempt < 100; attempt++) {

            Direction dir = validDirections.get(random.nextInt(validDirections.size()));

            int row;
            int col;

            switch (dir) {

                case RIGHT -> {
                    row = random.nextInt(rows);
                    col = random.nextInt((cols - word.length()) + 1);
                }

                case LEFT -> {
                    row = random.nextInt(rows);
                    col = random.nextInt(word.length() - 1, cols);
                }

                case DOWN -> {
                    row = random.nextInt((rows - word.length()) + 1);
                    col = random.nextInt(cols);
                }

                case UP -> {
                    row = random.nextInt(word.length() - 1, rows);
                    col = random.nextInt(cols);
                }

                default -> throw new InvalidDirectionException(dir.name());
            }

            if (!canPlaceBounds(word, row, col, dir, rows, cols)) {
                continue;
            }

            if (!canPlaceWord(grid, word, row, col, dir)) {
                continue;
            }

            place(grid, word, row, col, dir);

            return new PlacementDto(word, row, col, dir);
        }

        return null;
    }

    private boolean canPlaceBounds(String word, int row, int col, Direction dir, int rows, int cols) {

        int len = word.length();

        return switch (dir) {
            case RIGHT -> col + len <= cols;
            case LEFT -> col - len + 1 >= 0;
            case DOWN -> row + len <= rows;
            case UP -> row - len + 1 >= 0;
        };
    }

    private boolean canPlaceWord(char[][] grid, String word, int row, int col, Direction dir) {

        for (int i = 0; i < word.length(); i++) {

            int r = row;
            int c = col;

            switch (dir) {
                case RIGHT -> c += i;
                case LEFT -> c -= i;
                case DOWN -> r += i;
                case UP -> r -= i;
            }

            char existing = grid[r][c];

            if (existing != '\u0000' && existing != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private void place(char[][] grid, String word, int row, int col, Direction dir) {

        for (int i = 0; i < word.length(); i++) {

            switch (dir) {
                case RIGHT -> grid[row][col + i] = word.charAt(i);
                case LEFT -> grid[row][col - i] = word.charAt(i);
                case DOWN -> grid[row + i][col] = word.charAt(i);
                case UP -> grid[row - i][col] = word.charAt(i);
            }
        }
    }
}