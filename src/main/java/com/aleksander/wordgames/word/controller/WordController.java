package com.aleksander.wordgames.word.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.aleksander.wordgames.word.dto.WordFilterRequest;
import com.aleksander.wordgames.word.dto.WordResponse;
import com.aleksander.wordgames.word.enums.SortOrder;
import com.aleksander.wordgames.word.enums.SortType;
import com.aleksander.wordgames.word.service.WordService;

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
            @RequestParam(required = false) List<String> excludedWords,
            @RequestParam(defaultValue = "20") Integer limit,
            @RequestParam(required = false) Boolean random,
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
                excludedWords,
                limit,
                random,
                sort,
                order);

        return wordService.getWords(request);
    }
}