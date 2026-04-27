package com.aleksander.wordgames.findword.dto;

import java.time.Instant;
import java.util.List;

import com.aleksander.wordgames.common.enums.Direction;

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