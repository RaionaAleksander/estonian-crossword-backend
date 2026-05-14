package com.aleksander.wordgames.findword.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindWordResponse {
    private int rows;
    private int cols;

    private char[][] grid;

    private MainWordPlacementDto mainWord;

    private List<FindWordPlacementDto> clues;

    private Instant generatedAt;
}