package com.aleksander.crossword.clue.model;

import java.util.List;

public record WordMeaning(
        Long meaningId,
        List<String> definitions) {
}