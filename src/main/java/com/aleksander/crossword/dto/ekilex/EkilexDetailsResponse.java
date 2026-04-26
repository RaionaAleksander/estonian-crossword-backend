package com.aleksander.crossword.dto.ekilex;

import java.util.List;

import lombok.Data;

@Data
public class EkilexDetailsResponse {
    private List<DefinitionLangGroup> definitionLangGroups;
}