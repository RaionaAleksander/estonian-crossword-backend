package com.aleksander.wordgames.findword.dto;

import com.aleksander.wordgames.common.enums.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainWordPlacementDto {

    private String word;

    private int row;
    private int col;

    private Direction direction;
}