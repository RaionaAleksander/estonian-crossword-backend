package com.aleksander.wordgames.word.dto.request;

import com.aleksander.wordgames.word.dto.filter.WordFilterRequest;
import com.aleksander.wordgames.word.dto.filter.WordSortRequest;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordPageRequest {

    private WordFilterRequest filter;

    private WordSortRequest sort;

    private Integer page;

    private Integer size;
}