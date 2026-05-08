package com.aleksander.wordgames.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "word-loader")
public class WordLoaderProperties {

    private List<FileConfig> files = new ArrayList<>();

    @Data
    public static class FileConfig {
        private String file;
        private String category;
    }
}