package com.aleksander.wordgames.findword.validation;

import com.aleksander.wordgames.findword.dto.FindWordRequest;
import com.aleksander.wordgames.findword.exception.FindWordValidationException;

public class FindWordValidator {

    public static void validate(FindWordRequest request) {

        String mainWord = request.getMainWord().trim();
        Integer maxCrossLength = request.getMaxCrossLength();

        if (mainWord == null || mainWord.isBlank()) {
            throw new FindWordValidationException("mainWord must not be empty");
        }

        if (mainWord.length() < 2) {
            throw new FindWordValidationException("mainWord must contain at least 2 letters");
        }

        if (maxCrossLength == null) {
            throw new FindWordValidationException("maxCrossLength must not be null");
        }

        if (maxCrossLength < 2) {
            throw new FindWordValidationException("maxCrossLength must be >= 2");
        }

        if (maxCrossLength > 50) {
            throw new FindWordValidationException("maxCrossLength is too large");
        }
    }
}