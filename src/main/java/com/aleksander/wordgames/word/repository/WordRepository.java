package com.aleksander.wordgames.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleksander.wordgames.model.entity.Word;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByLengthBetween(int min, int max);

    boolean existsByLemma(String lemma);

    Optional<Word> findByLemma(String lemma);
}