package com.aleksander.wordgames.findword.dto;

import com.aleksander.wordgames.word.dto.WordFilterRequest;

import lombok.Data;

@Data
public class FindWordRequest {
    private String mainWord;
    private Integer maxCrossLength;
    private WordFilterRequest filter;
}