package com.aleksander.crossword.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.dto.WordFilterRequest;
import com.aleksander.crossword.dto.WordSearchRequest;
import com.aleksander.crossword.dto.WordSearchResponse;
import com.aleksander.crossword.model.enums.SortOrder;
import com.aleksander.crossword.model.enums.SortType;
import com.aleksander.crossword.service.WordSearchService;

@RestController
@RequestMapping("/api/games/word-search")
@RequiredArgsConstructor
public class WordSearchController {

    private final WordSearchService wordSearchService;

    @PostMapping("/generate")
    public WordSearchResponse generate(
            @RequestParam(defaultValue = "10") int rows,
            @RequestParam(defaultValue = "10") int cols,
            @RequestParam(defaultValue = "5") int wordsCount,
            @RequestParam(required = false) Boolean allowIncomplete,

            // filters
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) String startsWith,
            @RequestParam(required = false) String endsWith,
            @RequestParam(required = false) List<String> contains,
            @RequestParam(required = false) List<String> notContains,
            @RequestParam(required = false) String pattern) {
        WordFilterRequest filter = new WordFilterRequest(
                minLength,
                maxLength,
                startsWith,
                endsWith,
                contains,
                notContains,
                pattern,
                wordsCount, // limit
                true, // random
                SortType.LENGTH,
                SortOrder.DESC);

        WordSearchRequest request = new WordSearchRequest(
                rows,
                cols,
                wordsCount,
                allowIncomplete,
                filter);

        return wordSearchService.generate(request);
    }
}