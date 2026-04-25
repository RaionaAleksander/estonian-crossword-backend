package com.aleksander.crossword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class WordSearchResponse {

    private char[][] grid;

    private List<String> words;

    private List<PlacementDto> placements;

    private Instant generatedAt;

    private String warning;
}