package com.aleksander.crossword.dto.findword;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindWordClueDto {

    private int mainWordIndex;
    private int wordIndex;
    private String word;
    private String clue;
}