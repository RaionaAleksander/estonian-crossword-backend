package com.aleksander.crossword.config.external;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ekilex")
public class EkilexProperties {

    private String apiKey;
    private String baseUrl;
}