package com.aleksander.wordgames.word.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleksander.wordgames.model.entity.Word;
import com.aleksander.wordgames.model.entity.WordDefinition;

public interface WordDefinitionRepository extends JpaRepository<WordDefinition, Long> {
    List<WordDefinition> findByWord(Word word);
}