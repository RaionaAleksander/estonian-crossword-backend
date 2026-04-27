package com.aleksander.wordgames.clue.provider.ekilex.dto;

import java.util.List;

import lombok.Data;

@Data
public class DefinitionLangGroup {
    private String lang;
    private List<Definition> definitions;
}