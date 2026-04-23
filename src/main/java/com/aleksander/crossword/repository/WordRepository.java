package com.aleksander.crossword.repository;

import com.aleksander.crossword.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByLengthBetween(int min, int max);
}