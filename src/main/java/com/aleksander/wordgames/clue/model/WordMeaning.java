package com.aleksander.wordgames.clue.model;

import java.util.List;

public record WordMeaning(
                Long meaningId,
                List<String> definitions) {
}