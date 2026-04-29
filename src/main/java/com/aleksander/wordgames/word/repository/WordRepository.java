package com.aleksander.wordgames.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aleksander.wordgames.model.entity.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByLengthBetween(int min, int max);

    boolean existsByLemma(String lemma);
}