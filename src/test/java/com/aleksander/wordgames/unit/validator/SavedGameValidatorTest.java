package com.aleksander.wordgames.unit.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.aleksander.wordgames.common.enums.GameType;
import com.aleksander.wordgames.savedgame.exception.SavedGameValidationException;
import com.aleksander.wordgames.savedgame.validator.SavedGameValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SavedGameValidatorTest {

        private final ObjectMapper mapper = new ObjectMapper();

        @Test
        void shouldValidateFindWordPayload() throws Exception {
                String json = """
                                {
                                  "gameType": "FIND_WORD",
                                  "generatedAt": "2026-05-20T12:00:00Z",
                                  "rows": 5,
                                  "cols": 5,
                                  "grid": [],
                                  "mainWord": {},
                                  "clues": [],
                                  "filters": {}
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                GameType result = SavedGameValidator.validate(payload);

                assertEquals(GameType.FIND_WORD, result);
        }

        @Test
        void shouldValidateWordSearchPayload() throws Exception {

                String json = """
                                {
                                  "gameType": "WORD_SEARCH",
                                  "generatedAt": "2026-05-20T12:00:00Z",
                                  "rows": 10,
                                  "cols": 10,
                                  "grid": [],
                                  "words": [],
                                  "placements": [],
                                  "filters": {}
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                GameType result = SavedGameValidator.validate(payload);

                assertEquals(GameType.WORD_SEARCH, result);
        }

        @Test
        void shouldThrowWhenGameTypeInvalid() throws Exception {
                String json = """
                                {
                                  "gameType": "INVALID",
                                  "generatedAt": "2026-05-20T12:00:00Z"
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }

        @Test
        void shouldThrowWhenGeneratedAtMissing() throws Exception {
                String json = """
                                {
                                  "gameType": "FIND_WORD"
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }

        @Test
        void shouldThrowWhenGeneratedAtInvalid() throws Exception {

                String json = """
                                {
                                  "gameType": "FIND_WORD",
                                  "generatedAt": "invalid-date",
                                  "rows": 5,
                                  "cols": 5,
                                  "grid": [],
                                  "mainWord": {},
                                  "clues": [],
                                  "filters": {}
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }

        @Test
        void shouldThrowWhenRowsMissing() throws Exception {

                String json = """
                                {
                                  "gameType": "FIND_WORD",
                                  "generatedAt": "2026-05-20T12:00:00Z",
                                  "cols": 5,
                                  "grid": [],
                                  "mainWord": {},
                                  "clues": [],
                                  "filters": {}
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }

        @Test
        void shouldThrowWhenFiltersMissing() throws Exception {

                String json = """
                                {
                                  "gameType": "WORD_SEARCH",
                                  "generatedAt": "2026-05-20T12:00:00Z",
                                  "rows": 10,
                                  "cols": 10,
                                  "grid": [],
                                  "words": [],
                                  "placements": []
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }

        @Test
        void shouldThrowWhenWordSearchPayloadHasFindWordStructure() throws Exception {
                String json = """
                                {
                                  "gameType": "WORD_SEARCH",
                                  "generatedAt": "2026-05-20T12:00:00Z",
                                  "rows": 5,
                                  "cols": 5,
                                  "grid": [],
                                  "mainWord": {},
                                  "clues": [],
                                  "filters": {}
                                }
                                """;

                JsonNode payload = mapper.readTree(json);

                assertThrows(
                                SavedGameValidationException.class,
                                () -> SavedGameValidator.validate(payload));
        }
}
