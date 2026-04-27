package com.aleksander.wordgames.wordsearch.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.aleksander.wordgames.word.dto.WordFilterRequest;
import com.aleksander.wordgames.word.enums.SortOrder;
import com.aleksander.wordgames.word.enums.SortType;
import com.aleksander.wordgames.wordsearch.dto.WordSearchRequest;
import com.aleksander.wordgames.wordsearch.dto.WordSearchResponse;
import com.aleksander.wordgames.wordsearch.service.WordSearchService;

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
                        @RequestParam(required = false) List<String> excludedWords,
                        @RequestParam(required = false) String pattern) {
                WordFilterRequest filter = new WordFilterRequest(
                                minLength,
                                maxLength,
                                startsWith,
                                endsWith,
                                contains,
                                notContains,
                                pattern,
                                excludedWords,
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