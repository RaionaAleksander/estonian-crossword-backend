package com.aleksander.crossword.dto.ekilex;

import java.util.List;

import lombok.Data;

@Data
public class EkilexSearchResponse {
    private List<EkilexSearchResult> results;
}