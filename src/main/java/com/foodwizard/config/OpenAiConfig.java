package com.foodwizard.config;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi(openAiKey);
    }
}
