package com.aleksander.crossword.integration.savedgame;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aleksander.wordgames.EstonianCrosswordBackendApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = EstonianCrosswordBackendApplication.class)
@AutoConfigureMockMvc
public class SavedGameControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSaveGame() throws Exception {

        String json = """
                                                {
                  "gameType": "FIND_WORD",
                  "rows": 3,
                  "cols": 6,
                  "grid": [
                    "kuller",
                    " kiivi",
                    "island"
                  ],
                  "mainWord": {
                    "word": "uks",
                    "row": 0,
                    "col": 1,
                    "direction": "DOWN"
                  },
                  "clues": [
                    {
                      "word": "kuller",
                      "clue": "Inimene, kes toob pakid või toidu kiiresti kohale",
                      "row": 0,
                      "col": 0,
                      "direction": "RIGHT"
                    },
                    {
                      "word": "kiivi",
                      "clue": "Pruuni karvase koore ja rohelise sisuga hapukas vili",
                      "row": 1,
                      "col": 1,
                      "direction": "RIGHT"
                    },
                    {
                      "word": "island",
                      "clue": "Saareriik põhja-atlandil, mis on tuntud geisrite ja vulkaanide poolest",
                      "row": 2,
                      "col": 0,
                      "direction": "RIGHT"
                    }
                  ],
                  "filters": {
                    "minLength": null,
                    "maxLength": null,
                    "startsWith": null,
                    "endsWith": null,
                    "contains": null,
                    "notContains": null,
                    "includeCategories": null,
                    "excludeCategories": null,
                    "pattern": null,
                    "excludedWords": null
                  },
                  "generatedAt": "2026-05-20T09:54:54.732150900Z"
                }
                    """;

        mockMvc.perform(post("/api/v1/saved-games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.gameType").value("FIND_WORD"))
                .andReturn();
    }

    @Test
    void shouldSaveAndThenGetGame() throws Exception {

        String json = """
                                {
                  "gameType": "WORD_SEARCH",
                  "rows": 5,
                  "cols": 5,
                  "grid": [
                    "ipiti",
                    "vnpav",
                    "ikalt",
                    "ikpam",
                    "khust"
                  ],
                  "words": [
                    "salat",
                    "taani",
                    "kiivi"
                  ],
                  "placements": [
                    {
                      "word": "salat",
                      "row": 4,
                      "col": 3,
                      "direction": "UP"
                    },
                    {
                      "word": "taani",
                      "row": 4,
                      "col": 4,
                      "direction": "UP_LEFT"
                    },
                    {
                      "word": "kiivi",
                      "row": 4,
                      "col": 0,
                      "direction": "UP"
                    }
                  ],
                  "filters": {
                    "minLength": null,
                    "maxLength": 5,
                    "startsWith": null,
                    "endsWith": null,
                    "contains": null,
                    "notContains": null,
                    "includeCategories": null,
                    "excludeCategories": null,
                    "pattern": null,
                    "excludedWords": null
                  },
                  "generatedAt": "2026-05-20T09:53:51.888491400Z",
                  "warning": null
                }
                                """;

        MvcResult postResult = mockMvc.perform(post("/api/v1/saved-games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.gameType").value("WORD_SEARCH"))
                .andReturn();

        String response = postResult.getResponse().getContentAsString();
        JsonNode node = objectMapper.readTree(response);

        Long id = node.get("id").asLong();

        mockMvc.perform(get("/api/v1/saved-games/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameType").value("WORD_SEARCH"))
                .andExpect(jsonPath("$.rows").value(5))
                .andExpect(jsonPath("$.cols").value(5))
                .andExpect(jsonPath("$.grid").isArray())
                .andExpect(jsonPath("$.words").isArray())
                .andExpect(jsonPath("$.placements").isArray())
                .andExpect(jsonPath("$.generatedAt").exists());
    }
}
