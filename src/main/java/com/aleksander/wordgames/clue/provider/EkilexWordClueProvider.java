package com.aleksander.wordgames.clue.provider;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.aleksander.wordgames.clue.exception.WordClueFetchException;
import com.aleksander.wordgames.clue.model.WordMeaning;
import com.aleksander.wordgames.clue.provider.ekilex.dto.Definition;
import com.aleksander.wordgames.clue.provider.ekilex.dto.DefinitionLangGroup;
import com.aleksander.wordgames.clue.provider.ekilex.dto.EkilexDetailsResponse;
import com.aleksander.wordgames.clue.provider.ekilex.dto.EkilexSearchResponse;
import com.aleksander.wordgames.clue.provider.ekilex.dto.EkilexSearchResult;
import com.aleksander.wordgames.config.external.EkilexProperties;

import java.util.*;

@Component
@RequiredArgsConstructor
public class EkilexWordClueProvider implements WordClueProvider {

    private final RestTemplate restTemplate;
    private final EkilexProperties properties;

    @Override
    @Cacheable(value = "ekilex_meanings", key = "#lemma.toLowerCase()", unless = "#result == null || #result.isEmpty()")
    public List<WordMeaning> getMeanings(String lemma) {

        List<WordMeaning> meanings = new ArrayList<>();

        try {
            String searchUrl = properties.getBaseUrl() + "/meaning/search/" + lemma + "/eki";

            HttpHeaders headers = new HttpHeaders();
            headers.set("ekilex-api-key", properties.getApiKey());

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<EkilexSearchResponse> searchResponse = restTemplate.exchange(searchUrl, HttpMethod.GET,
                    entity, EkilexSearchResponse.class);

            List<EkilexSearchResult> results = searchResponse.getBody().getResults();

            if (results == null || results.isEmpty()) {
                return meanings;
            }

            for (EkilexSearchResult result : results) {

                Long meaningId = result.getMeaningId();

                if (meaningId == null) {
                    continue;
                }

                String detailsUrl = properties.getBaseUrl() + "/meaning/details/" + meaningId;

                ResponseEntity<EkilexDetailsResponse> detailsResponse = restTemplate.exchange(detailsUrl,
                        HttpMethod.GET, entity, EkilexDetailsResponse.class);

                List<DefinitionLangGroup> groups = detailsResponse.getBody().getDefinitionLangGroups();

                if (groups == null) {
                    continue;
                }

                List<String> definitions = new ArrayList<>();

                for (DefinitionLangGroup group : groups) {

                    if (!"est".equals(group.getLang())) {
                        continue;
                    }

                    List<Definition> defs = group.getDefinitions();

                    if (defs == null) {
                        continue;
                    }

                    for (Definition def : defs) {
                        String value = def.getValue().trim();

                        if (isValidDefinition(value)) {
                            definitions.add(normalizeDefinition(value));
                        }
                    }
                }

                if (!definitions.isEmpty()) {
                    meanings.add(new WordMeaning(meaningId, definitions));
                }
            }

        } catch (Exception e) {
            throw new WordClueFetchException(
                    "Failed to fetch clue from Ekilex for word: " + lemma, e);
        }

        return meanings;
    }

    // ---------------- helpers ----------------

    private boolean isValidDefinition(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        if (value.startsWith("(") && value.endsWith(")")) {
            return false;
        }

        return true;
    }

    private String normalizeDefinition(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }
}