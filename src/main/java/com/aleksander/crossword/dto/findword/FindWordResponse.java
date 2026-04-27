package com.aleksander.crossword.dto.findword;

import java.time.Instant;
import java.util.List;

import com.aleksander.crossword.model.enums.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindWordResponse {
    private String mainWord;
    private Direction mainWordDirection;
    private int mainWordGridIndex;
    private int gridSize;
    private List<FindWordClueDto> clues;
    private Instant generatedAt;
}