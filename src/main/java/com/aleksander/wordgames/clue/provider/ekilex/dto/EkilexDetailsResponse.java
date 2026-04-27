package com.aleksander.wordgames.clue.provider.ekilex.dto;

import java.util.List;

import lombok.Data;

@Data
public class EkilexDetailsResponse {
    private List<DefinitionLangGroup> definitionLangGroups;
}