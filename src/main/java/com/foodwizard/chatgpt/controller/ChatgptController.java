package com.foodwizard.chatgpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bot")
public class ChatgptController {

    @Value("${openai.model}")
    private String model;

    private final OpenAiApi openAiApi;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/chat")
    public List<String> chat(@RequestParam String prompt) {
        return getFoodList(getChatCompletion(prompt));
    }

    private ResponseEntity<OpenAiApi.ChatCompletion> getChatCompletion(String prompt) {
        StringBuilder sb = new StringBuilder();
        sb.append("나의 현재 기분은 ")
                .append(prompt)
                .append("이다.\n")
                .append("나의 기분에 어울리는 점심 식사를 하려고 한다.")
                .append("나의 기분에 맞는 점심 식사 메뉴로 적절한 음식 종류를 3가지 추천해줘.")
                .append("디저트와 관련 된 음식은 제외한다.")
                .append("결과는 [음식;음식;음식3] 형태로 나와야 한다.");
        OpenAiApi.ChatCompletionMessage message = new OpenAiApi.ChatCompletionMessage(sb.toString(), OpenAiApi.ChatCompletionMessage.Role.USER);
        OpenAiApi.ChatCompletionRequest request = new OpenAiApi.ChatCompletionRequest(Arrays.asList(message), model, 0.7F);

        return openAiApi.chatCompletionEntity(request);
    }

    private List<String> getFoodList(ResponseEntity<OpenAiApi.ChatCompletion> response) {
        List<String> foodList = Arrays.stream(
                response.getBody().choices().get(0).message().content()
                        .replace("[","")
                        .replace("]", "").split(";")
                ).map(String::trim).collect(Collectors.toList());
        return foodList;
    }
}
