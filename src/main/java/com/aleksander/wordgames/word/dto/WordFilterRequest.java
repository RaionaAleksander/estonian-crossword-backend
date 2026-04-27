package com.aleksander.wordgames.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.aleksander.wordgames.word.enums.SortOrder;
import com.aleksander.wordgames.word.enums.SortType;

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

    private List<String> excludedWords;

    private Integer limit;

    private Boolean random;

    private SortType sort;
    private SortOrder order;
}