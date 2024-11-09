package com.youragent.service.aiservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youragent.service.restservice.RestService;
import com.youragent.util.Constants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AIServiceImpl implements AIService {

    private final String openAiKey = System.getenv("OPENAI_KEY");

    private final Map<String, String> headers = Map.of(
            HttpHeaders.CONTENT_TYPE, "application/json"
    );

    private final RestService<JsonNode> restService;

    private final ObjectMapper objectMapper;

    public AIServiceImpl(@NonNull RestService<JsonNode> restService,
                         @NonNull ObjectMapper objectMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    public String processAIQuickFact(@NonNull String place) {
        log.info("processing processAIQuickFact({})", place);

        String promptBody =
            "{\n" +
            "    \"model\": \"" + Constants.OPEN_AI_MODEL + "\",\n" +
            "    \"messages\": [\n" +
            "        {\n" +
            "            \"role\": \"user\",\n" +
            "            \"content\": \"" + String.format(Constants.QUICK_FACT_PROMPT, place, place) + "\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"max_tokens\": " + Constants.OPEN_AI_MAX_TOKENS + "\n" +
            "}";

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::set);
        httpHeaders.setBearerAuth(openAiKey);

        final JsonNode jsonNode = restService.postRequest(Constants.OPEN_AI_URI, httpHeaders, promptBody, JsonNode.class);

        log.info(jsonNode.toPrettyString());

        try {
            final JsonNode choicesList = jsonNode.get("choices");

            if (choicesList.isArray()) {
                for (final JsonNode node: choicesList) {
                    final String content = node.get("message").get("content").asText();
                    log.info(content);
                    return content;
                }
            }
        } catch (NullPointerException e) {
            log.warn("Encountered a null node while drilling response object", e);
        }

        return "";
    }
}


