package com.aleksander.crossword.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.aleksander.crossword.dto.WordDto;
import com.aleksander.crossword.dto.WordFilterRequest;
import com.aleksander.crossword.dto.WordResponse;
import com.aleksander.crossword.exception.InvalidSortException;
import com.aleksander.crossword.model.entity.Word;
import com.aleksander.crossword.model.enums.SortOrder;
import com.aleksander.crossword.repository.WordRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    public WordResponse getWords(WordFilterRequest request) {

        List<Word> words = wordRepository.findAll();

        List<WordDto> filtered = words.stream()
                .filter(w -> filterByLength(w, request))
                .filter(w -> filterByStartsWith(w, request))
                .filter(w -> filterByEndsWith(w, request))
                .filter(w -> filterByContains(w, request))
                .filter(w -> filterByNotContains(w, request))
                .filter(w -> filterByPattern(w, request))
                .map(this::toDto)
                .collect(Collectors.toList());

        filtered = sort(filtered, request);

        if (request.getLimit() != null && request.getLimit() < filtered.size()) {
            filtered = filtered.subList(0, request.getLimit());
        }

        return new WordResponse(
                filtered.size(),
                filtered,
                Instant.now());
    }

    // ---------------- helpers ----------------

    private boolean filterByLength(Word w, WordFilterRequest r) {
        if (r.getMinLength() == null && r.getMaxLength() == null)
            return true;

        int len = w.getLength();

        if (r.getMinLength() != null && len < r.getMinLength())
            return false;
        if (r.getMaxLength() != null && len > r.getMaxLength())
            return false;

        return true;
    }

    private boolean filterByStartsWith(Word w, WordFilterRequest r) {
        if (r.getStartsWith() == null)
            return true;
        return w.getLemma().startsWith(r.getStartsWith());
    }

    private boolean filterByEndsWith(Word w, WordFilterRequest r) {
        if (r.getEndsWith() == null)
            return true;
        return w.getLemma().endsWith(r.getEndsWith());
    }

    private boolean filterByContains(Word w, WordFilterRequest r) {
        if (r.getContains() == null || r.getContains().isEmpty())
            return true;

        for (String c : r.getContains()) {
            if (!w.getLemma().contains(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean filterByNotContains(Word w, WordFilterRequest r) {
        if (r.getNotContains() == null)
            return true;

        for (String c : r.getNotContains()) {
            if (w.getLemma().contains(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean filterByPattern(Word w, WordFilterRequest r) {
        if (r.getPattern() == null)
            return true;

        String word = w.getLemma();
        String pattern = r.getPattern();

        if (word.length() != pattern.length())
            return false;

        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            if (p != '_' && p != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    private WordDto toDto(Word w) {
        return new WordDto(w.getId(), w.getLemma(), w.getLength());
    }

    private List<WordDto> sort(List<WordDto> list, WordFilterRequest r) {
        if (r.getSort() == null) {
            return list;
        }

        Comparator<WordDto> comparator = switch (r.getSort()) {

            case LENGTH -> Comparator.comparing(WordDto::getLength);
            case ALPHABET -> Comparator.comparing(WordDto::getLemma);

            default -> throw new InvalidSortException("Unsupported sort type: " + r.getSort());
        };

        if (comparator == null)
            return list;

        if (r.getOrder() == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return list.stream()
                .sorted(comparator)
                .toList();
    }
}