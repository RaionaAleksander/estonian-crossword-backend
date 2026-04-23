package com.aleksander.crossword.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.dto.WordFilterRequest;
import com.aleksander.crossword.dto.WordResponse;
import com.aleksander.crossword.model.enums.SortOrder;
import com.aleksander.crossword.model.enums.SortType;
import com.aleksander.crossword.service.WordService;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping
    public WordResponse getWords(
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) String startsWith,
            @RequestParam(required = false) String endsWith,
            @RequestParam(required = false) List<String> contains,
            @RequestParam(required = false) List<String> notContains,
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(required = false) SortType sort,
            @RequestParam(required = false) SortOrder order) {

        WordFilterRequest request = new WordFilterRequest(
                minLength,
                maxLength,
                startsWith,
                endsWith,
                contains,
                notContains,
                pattern,
                limit,
                sort,
                order);

        return wordService.getWords(request);
    }
}