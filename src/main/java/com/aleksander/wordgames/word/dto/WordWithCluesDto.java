package com.aleksander.wordgames.word.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordWithCluesDto {
    private String word;
    private List<String> definitions;
}