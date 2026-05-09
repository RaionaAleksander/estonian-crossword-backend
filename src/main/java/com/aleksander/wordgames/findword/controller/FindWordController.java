package com.aleksander.wordgames.findword.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aleksander.wordgames.common.enums.Direction;
import com.aleksander.wordgames.findword.dto.FindWordRequest;
import com.aleksander.wordgames.findword.dto.FindWordResponse;
import com.aleksander.wordgames.findword.service.FindWordService;
import com.aleksander.wordgames.word.dto.filter.WordFilterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games/find-word")
@RequiredArgsConstructor
public class FindWordController {

    private final FindWordService findWordService;

    @PostMapping("/generate")
    public FindWordResponse generate(
            @RequestParam String mainWord,
            @RequestParam(defaultValue = "10") Integer maxCrossLength,
            @RequestParam(required = false) Integer mainWordGridIndex,
            @RequestParam(required = false) Direction mainWordDirection,

            // filters
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) String startsWith,
            @RequestParam(required = false) String endsWith,
            @RequestParam(required = false) List<String> contains,
            @RequestParam(required = false) List<String> notContains,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> excludedCategories,
            @RequestParam(required = false) String pattern,
            @RequestParam(required = false) List<String> excludedWords) {

        WordFilterRequest filter = new WordFilterRequest(
                minLength,
                maxLength,
                startsWith,
                endsWith,
                contains,
                notContains,
                categories,
                excludedCategories,
                pattern,
                excludedWords);

        FindWordRequest request = new FindWordRequest(
                mainWord,
                maxCrossLength,
                mainWordGridIndex,
                mainWordDirection,
                filter);

        return findWordService.generate(request);
    }
}