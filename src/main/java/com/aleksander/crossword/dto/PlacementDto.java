package com.aleksander.crossword.dto;

import com.aleksander.crossword.model.enums.Direction;
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