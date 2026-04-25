package com.aleksander.crossword.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.aleksander.crossword.model.enums.SortOrder;
import com.aleksander.crossword.model.enums.SortType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordFilterRequest {

    private Integer minLength;
    private Integer maxLength;

    private String startsWith;
    private String endsWith;

    private List<String> contains;
    private List<String> notContains;

    private String pattern;

    private Integer limit;

    private SortType sort;
    private SortOrder order;
}