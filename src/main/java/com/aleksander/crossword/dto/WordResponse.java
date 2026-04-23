package com.aleksander.crossword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class WordResponse {

    private int count;
    private List<WordDto> words;
    private Instant generatedAt;
}