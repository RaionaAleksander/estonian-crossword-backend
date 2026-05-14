package com.aleksander.wordgames.findword.dto;

import com.aleksander.wordgames.common.enums.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindWordPlacementDto {

    private String word;

    private String clue;

    private int row;
    private int col;

    private Direction direction;
}