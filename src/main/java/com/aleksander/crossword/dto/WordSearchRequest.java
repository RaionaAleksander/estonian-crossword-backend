package com.aleksander.crossword.dto;

import lombok.Data;

@Data
public class WordSearchRequest {

    private int rows;
    private int cols;

    private int wordsCount;

    private Integer minLength;
    private Integer maxLength;
}