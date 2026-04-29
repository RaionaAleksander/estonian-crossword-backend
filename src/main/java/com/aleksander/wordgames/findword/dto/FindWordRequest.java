package com.aleksander.wordgames.findword.dto;

import com.aleksander.wordgames.common.enums.Direction;
import com.aleksander.wordgames.word.dto.WordFilterRequest;

import lombok.Data;

@Data
public class FindWordRequest {
    private String mainWord;
    private Integer maxCrossLength;
    private Integer mainWordGridIndex;
    private Direction mainWordDirection;
    private WordFilterRequest filter;
}