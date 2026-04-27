package com.aleksander.wordgames.clue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.aleksander.wordgames.clue.exception.WordClueNotFoundException;
import com.aleksander.wordgames.clue.model.WordClue;
import com.aleksander.wordgames.clue.service.WordClueService;

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