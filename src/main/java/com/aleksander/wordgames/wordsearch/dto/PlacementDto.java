package com.aleksander.wordgames.wordsearch.dto;

import com.aleksander.wordgames.common.enums.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlacementDto {

    private String word;

    private int row;
    private int col;

    private Direction direction;
}