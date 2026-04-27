package com.aleksander.wordgames.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordDto {

    private Long id;
    private String lemma;
    private int length;
}