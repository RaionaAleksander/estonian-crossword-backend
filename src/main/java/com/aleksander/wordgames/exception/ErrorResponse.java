package com.aleksander.wordgames.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private String error;
    private Instant timestamp;
    private String path;
}