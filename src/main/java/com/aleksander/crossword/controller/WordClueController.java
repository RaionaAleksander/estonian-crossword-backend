package com.aleksander.crossword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.aleksander.crossword.clue.model.WordClue;
import com.aleksander.crossword.clue.service.WordClueService;
import com.aleksander.crossword.exception.wordclue.WordClueNotFoundException;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordClueController {

    private final WordClueService wordClueService;

    @GetMapping("/clue")
    public WordClue getClue(@RequestParam String word) {
        return wordClueService.getClue(word).orElseThrow(() -> new WordClueNotFoundException(word));
    }
}