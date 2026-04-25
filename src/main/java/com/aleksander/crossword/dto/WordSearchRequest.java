package com.aleksander.crossword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordSearchRequest {

    private int rows;
    private int cols;

    private int wordsCount;

    private Boolean allowIncomplete;

    private WordFilterRequest filter;
}