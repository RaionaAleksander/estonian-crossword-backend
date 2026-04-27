package com.aleksander.crossword.dto.findword;

import lombok.Data;

@Data
public class FindWordRequest {
    private String mainWord;
    private Integer maxCrossLength;
}