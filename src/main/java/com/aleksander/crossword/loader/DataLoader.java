package com.aleksander.crossword.loader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aleksander.crossword.model.entity.Word;
import com.aleksander.crossword.repository.WordRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final WordRepository wordRepository;

    @Override
    public void run(String... args) throws Exception {

        if (wordRepository.count() > 0) {
            return;
        }

        InputStream is = getClass()
                .getResourceAsStream("/data/estonian_nouns.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .forEach(word -> {

                    Word entity = Word.builder()
                            .lemma(word)
                            .length(word.length())
                            .build();

                    wordRepository.save(entity);
                });

        System.out.println("Words loaded successfully!");
    }
}