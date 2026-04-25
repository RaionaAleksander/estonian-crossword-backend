package com.aleksander.crossword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.dto.WordSearchRequest;
import com.aleksander.crossword.dto.WordSearchResponse;
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
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxLength) {
        WordSearchRequest request = new WordSearchRequest();
        request.setRows(rows);
        request.setCols(cols);
        request.setWordsCount(wordsCount);
        request.setMinLength(minLength);
        request.setMaxLength(maxLength);

        return wordSearchService.generate(request);
    }
}