package com.aleksander.wordgames.findword.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aleksander.wordgames.findword.dto.FindWordRequest;
import com.aleksander.wordgames.findword.dto.FindWordResponse;
import com.aleksander.wordgames.findword.service.FindWordService;

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