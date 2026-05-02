package com.aleksander.wordgames.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aleksander.wordgames.common.dto.WordJson;
import com.aleksander.wordgames.model.entity.Word;
import com.aleksander.wordgames.model.entity.WordDefinition;
import com.aleksander.wordgames.word.repository.WordDefinitionRepository;
import com.aleksander.wordgames.word.repository.WordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;
    private final WordDefinitionRepository definitionRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {

        if (wordRepository.count() > 0) {
            return;
        }

        loadFromFile("loomad.json", "LOOMAD");
        loadFromFile("toit.json", "TOIT");
        loadFromFile("ametid.json", "AMETID");

        System.out.println("Words + definitions loaded!");
    }

    private void loadFromFile(String fileName, String category) throws Exception {

        InputStream is = getClass()
                .getResourceAsStream("/data/" + fileName);

        List<WordJson> words = objectMapper.readValue(
                is,
                new TypeReference<>() {
                });

        for (WordJson json : words) {

            Word word = Word.builder()
                    .lemma(json.getWord().toLowerCase())
                    .length(json.getWord().length())
                    .category(category)
                    .build();

            wordRepository.save(word);

            for (String def : json.getDefinitions()) {

                WordDefinition definition = new WordDefinition();
                definition.setWord(word);
                definition.setDefinition(def);

                definitionRepository.save(definition);
            }
        }
    }
}