package com.aleksander.wordgames.savedgame.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.aleksander.wordgames.common.enums.GameType;
import com.aleksander.wordgames.model.entity.SavedGame;
import com.aleksander.wordgames.savedgame.dto.SavedGameResponse;
import com.aleksander.wordgames.savedgame.exception.SavedGameNotFoundException;
import com.aleksander.wordgames.savedgame.exception.SavedGameParseException;
import com.aleksander.wordgames.savedgame.repository.SavedGameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedGameService {

    private final SavedGameRepository repository;
    private final ObjectMapper objectMapper;

    public JsonNode getById(Long id) {

        SavedGame game = repository.findById(id)
                .orElseThrow(() -> new SavedGameNotFoundException(id));

        try {
            return objectMapper.readTree(game.getPayload());

        } catch (JsonProcessingException e) {
            throw new SavedGameParseException(
                    "Failed to parse saved game payload");
        }
    }

    public SavedGameResponse save(JsonNode payload) {

        GameType type = detectGameType(payload);

        System.out.println(payload.toPrettyString());

        SavedGame entity = new SavedGame();
        entity.setGameType(type);
        entity.setPayload(payload.toString());

        System.out.println(entity.getPayload());
        entity.setCreatedAt(Instant.now());

        repository.save(entity);

        return new SavedGameResponse(
                entity.getId(),
                entity.getGameType(),
                entity.getCreatedAt());
    }

    // ---------------- helpers ----------------

    private GameType detectGameType(JsonNode payload) {

        if (payload.has("grid") && payload.has("placements")) {
            return GameType.WORD_SEARCH;
        }

        if (payload.has("mainWord") && payload.has("clues")) {
            return GameType.FIND_WORD;
        }

        throw new IllegalArgumentException("Unknown game type");
    }
}
