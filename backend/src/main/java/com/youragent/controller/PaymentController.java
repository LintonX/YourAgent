package com.youragent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.youragent.model.Agent;
import com.youragent.service.paymentservice.PaymentServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "${frontend.address}")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(@NonNull final PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/auth/createStripeSession")
    public ResponseEntity<String> createStripeSession(@RequestBody final Agent agent) {

        try {
            String checkoutSessionUrl = paymentService.createStripePaymentSession(agent);
            return ResponseEntity.status(200).body(checkoutSessionUrl);
        } catch (StripeException e) {
            log.error("Exception when creating Stripe session", e);
        } catch (JsonProcessingException e) {
            log.error("Exception when parsing county list of Agent");
        }

        return ResponseEntity.status(400).body(null);
    }

    @PostMapping(path = "/auth/webhook/stripe")
    public ResponseEntity<Long> stripeWebhookListener(@RequestBody final String payload,
                                                   @RequestHeader("Stripe-Signature") final String sig) {

        log.info("POST: At stripeWebhookListener");

        try {
            Long credentialIdOfAgent = paymentService.handleWebhookEvent(payload, sig);

            if (credentialIdOfAgent != null) {
                // payment was processed, return 201 "created"
                return ResponseEntity.status(201).body(credentialIdOfAgent);
            } else {
                // payment process was cancelled or diverted, return 200 "ok"
                return ResponseEntity.status(200).body(null);
            }
        } catch (SignatureVerificationException e) {
            log.error("Webhook signature invalid", e);
        } catch (RuntimeException e) {
            log.error("Encountered an error while processing stripe payment", e);
        } catch (JsonProcessingException e) {
            log.error("Encountered an error while parsing stripe metadata", e);
        }

        return ResponseEntity.status(401).body(null);
    }
}
