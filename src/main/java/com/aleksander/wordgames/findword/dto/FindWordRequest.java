package com.aleksander.wordgames.findword.dto;

import lombok.Data;

@Data
public class FindWordRequest {
    private String mainWord;
    private Integer maxCrossLength;
}