package com.aleksander.wordgames.common.dto;

import java.util.List;

import lombok.Data;

@Data
public class WordJson {
    private String word;
    private List<String> definitions;
}