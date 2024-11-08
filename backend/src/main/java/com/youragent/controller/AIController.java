package com.youragent.controller;

import com.youragent.service.aiservice.AIService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "${frontend.address}")
public class AIController {

    private final AIService aiService;

    public AIController(@NonNull final AIService aiService){
        this.aiService = aiService;
    }

    @GetMapping(path = "/locationFact")
    public ResponseEntity<?> getQuickAIFact(@RequestParam("place") @NonNull String place) {
        log.info(String.format("GET: getQuickAIFact place=%s,", place));

        final String quickFact = aiService.processAIQuickFact(place);

        if (quickFact != null && !quickFact.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(quickFact);
        }

        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(null);
    }
}
