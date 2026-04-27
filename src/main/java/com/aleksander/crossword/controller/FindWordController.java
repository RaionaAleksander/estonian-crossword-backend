package com.aleksander.crossword.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aleksander.crossword.dto.findword.FindWordRequest;
import com.aleksander.crossword.dto.findword.FindWordResponse;
import com.aleksander.crossword.service.FindWordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/games/find-word")
@RequiredArgsConstructor
public class FindWordController {

    private final FindWordService findWordService;

    @PostMapping("/generate")
    public FindWordResponse generate(
            @RequestParam String mainWord,
            @RequestParam(defaultValue = "10") Integer maxCrossLength) {

        FindWordRequest request = new FindWordRequest();
        request.setMainWord(mainWord);
        request.setMaxCrossLength(maxCrossLength);

        return findWordService.generate(request);
    }
}