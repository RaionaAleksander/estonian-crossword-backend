package com.aleksander.wordgames.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.aleksander.wordgames.model.entity.Word;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    boolean existsByLemma(String lemma);

    Optional<Word> findByLemma(String lemma);
}