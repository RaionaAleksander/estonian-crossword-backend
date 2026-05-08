package com.aleksander.wordgames.word.dto.request;

import com.aleksander.wordgames.word.dto.filter.WordFilterRequest;
import com.aleksander.wordgames.word.dto.filter.WordSortRequest;

import lombok.*;

@Data
@AllArgsConstructor
public class WordListRequest {

    private WordFilterRequest filter;

    private WordSortRequest sort;

    private Integer limit;

    private Boolean random;
}