package com.aleksander.crossword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.service.WordClueService;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordClueController {

    private final WordClueService wordClueService;

    @GetMapping("/clue")
    public String getClue(@RequestParam String word) {
        return wordClueService.getClue(word);
    }
}