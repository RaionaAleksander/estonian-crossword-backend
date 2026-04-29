package com.aleksander.wordgames.findword.validation;

import com.aleksander.wordgames.findword.dto.FindWordRequest;
import com.aleksander.wordgames.findword.exception.FindWordValidationException;

public class FindWordValidator {

    public static void validate(FindWordRequest request) {

        if (request.getMainWord() == null || request.getMainWord().isBlank()) {
            throw new FindWordValidationException("mainWord must not be empty");
        }

        String mainWord = request.getMainWord().trim().toLowerCase();

        if (mainWord.length() < 2) {
            throw new FindWordValidationException("mainWord must contain at least 2 letters");
        }

        Integer maxCrossLength = request.getMaxCrossLength();

        if (maxCrossLength == null) {
            throw new FindWordValidationException("maxCrossLength must not be null");
        }

        if (maxCrossLength < 2) {
            throw new FindWordValidationException("maxCrossLength must be >= 2");
        }

        if (maxCrossLength > 50) {
            throw new FindWordValidationException("maxCrossLength is too large");
        }

        Integer min = request.getFilter().getMinLength();
        Integer max = request.getFilter().getMaxLength();

        if (min != null && min < 1) {
            throw new FindWordValidationException("minLength must be >= 1");
        }

        if (min != null && max != null && max < min) {
            throw new FindWordValidationException("maxLength must be >= minLength");
        }

        if (max != null && max > maxCrossLength) {
            throw new FindWordValidationException("maxLength must be <= maxCrossLength");
        }

        Integer mainWordGridIndex = request.getMainWordGridIndex();

        if (mainWordGridIndex != null) {
            if (mainWordGridIndex < 0 || mainWordGridIndex >= maxCrossLength) {
                throw new FindWordValidationException("mainWordGridIndex must be within grid bounds");
            }
        }
    }
}