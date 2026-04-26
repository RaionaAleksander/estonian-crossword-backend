package com.aleksander.crossword.dto.ekilex;

import java.util.List;

import lombok.Data;

@Data
public class DefinitionLangGroup {
    private String lang;
    private List<Definition> definitions;
}