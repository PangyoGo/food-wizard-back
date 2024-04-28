package com.foodwizard.chatgpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bot")
public class ChatgptController {

    @Value("${openai.model}")
    private String model;


    private final OpenAiApi openAiApi;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt){
        OpenAiApi.ChatCompletionMessage message = new OpenAiApi.ChatCompletionMessage(prompt, OpenAiApi.ChatCompletionMessage.Role.USER);
        OpenAiApi.ChatCompletionRequest request = new OpenAiApi.ChatCompletionRequest(Arrays.asList(message), model, 0.7F);
        ResponseEntity<OpenAiApi.ChatCompletion> result = openAiApi.chatCompletionEntity(request);
        return result.getBody().toString();
    }
}
