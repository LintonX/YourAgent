package com.youragent.service.paymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.youragent.dao.authdao.AuthDaoImpl;
import com.youragent.model.Agent;
import com.youragent.model.AgentTier;
import com.youragent.service.agentservice.AgentServiceImpl;
import com.youragent.service.authservice.AuthService;
import com.youragent.service.authservice.AuthServiceImpl;
import com.youragent.util.Constants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.key.secret}")
    private String stripeSecretKey;

    @Value("${stripe.key.secret.webhook}")
    private String stripeWebhookSecretKey;

    @Value("${frontend.address}")
    private String returnToAddress;

    @Value("${stripe.price.basic}")
    private String basicPriceId;

    @Value("${stripe.price.standard}")
    private String standardPriceId;

    @Value("${stripe.price.premium}")
    private String premiumPriceId;

    private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";

    private static final String PAYMENT_INTENT_CANCELED = "payment_intent.canceled";

    private static final String CUSTOMER_SUBSCRIPTION_DELETED = "customer.subscription.deleted";

    private static final String SUBSCRIPTION_ABORTED = "subscription_schedule.aborted";

    private static final String SUBSCRIPTION_CANCELLED = "subscription_schedule.canceled";

    private final AuthServiceImpl authService;

    @Autowired
    private final Gson gson;

    public PaymentServiceImpl(@NonNull final AuthServiceImpl authService,
                              @NonNull final Gson gson) {
        this.authService = authService;
        this.gson = gson;
    }

    public String createStripePaymentSession(@NonNull final Agent agent) throws StripeException, JsonProcessingException {

        log.info("Attempting to create stripe payment session for {}", agent);

        Stripe.apiKey = stripeSecretKey;

        log.info(Stripe.apiKey);

        SessionCreateParams params = SessionCreateParams.builder()
                        .setClientReferenceId(String.valueOf(agent.getId()))
                        .setCustomerEmail(agent.getEmail())
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                                .setProduct(getSessionProductId(agent.getTier()))
                                                .setUnitAmount(getSessionPriceId(agent.getTier()))
                                                .setCurrency("USD")
                                                .setRecurring(SessionCreateParams.LineItem.PriceData.Recurring.builder()
                                                        .setInterval(SessionCreateParams.LineItem.PriceData.Recurring.Interval.MONTH)
                                                        .build())
                                                .build())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                        .setCancelUrl(returnToAddress + "/cancelled")
                        .setSuccessUrl(returnToAddress + "/success")
                        .putMetadata("tier", agent.getTier().toString())
                        .putMetadata("selected_state", agent.getSelectedState())
                        .putMetadata("selected_counties", gson.toJson(agent.getSelectedCounties()))
                        .build();

        Session session = Session.create(params);

        log.info(session.toJson());

        log.info("Stripe session url = {}", session.getUrl());

        return session.getUrl();
    }

    public Long handleWebhookEvent(@NonNull final String payload,
                                   @NonNull final String sig) throws SignatureVerificationException, JsonProcessingException {

        final Event event = Webhook.constructEvent(payload, sig, stripeWebhookSecretKey);

        final EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

        if (dataObjectDeserializer.getObject().isEmpty()) {
            throw new RuntimeException("Deserialization failed, probably due to an API version mismatch.");
        }

        JsonObject jsonObjectParsed = JsonParser.parseString(dataObjectDeserializer.getObject().get().toJson()).getAsJsonObject();


        switch (event.getType()) {

            case CHECKOUT_SESSION_COMPLETED -> {

                final Long credentialIdOfAgent = extractCredentialId(jsonObjectParsed);
                final String agentEmail = extractEmail(jsonObjectParsed);

                if (jsonObjectParsed.has("metadata")) {
                    final JsonObject metadataObject = jsonObjectParsed.getAsJsonObject("metadata");
                    final AgentTier tier = extractTier(metadataObject);
                    final String selectedState = extractState(metadataObject);
                    final List<String> selectedCounties = extractCounties(metadataObject);

                    final Agent agent = Agent.builder()
                            .email(agentEmail)
                            .tier(tier)
                            .hasAccess(true)
                            .selectedState(selectedState)
                            .selectedCounties(selectedCounties)
                            .build();

                    authService.mapSavedAgentToAgentCredentials(agent, credentialIdOfAgent);
                }

                log.info("SESSION COMPLETED!!!!");
                return credentialIdOfAgent;
            }

            case PAYMENT_INTENT_CANCELED -> {
                log.info("PAYMENT CANCELED!!!!!");
                return null;
            }

            case CUSTOMER_SUBSCRIPTION_DELETED -> {
                log.info("SUBSCRIPTION DELETED!!!!!");
            }

            case SUBSCRIPTION_CANCELLED -> {
                log.info("SUBSCRIPTION CANCELED!!!!!");
            }

            case SUBSCRIPTION_ABORTED -> {
                log.info("SUBSCRIPTION ABORTED!!!!!");
            }

            default -> log.info("Unhandled event type: {}", event.getType());
        }
        return null;
    }

    private String extractEmail(@NonNull final JsonObject jsonObjectParsed) {
        if (jsonObjectParsed.has("customer_email")) {
            String agentEmail = jsonObjectParsed.get("customer_email").getAsString();
            log.info("Email of agent = {}", agentEmail);
            return agentEmail;
        }
        return null;
    }

    private Long extractCredentialId(@NonNull final JsonObject jsonObjectParsed) {
        if (jsonObjectParsed.has("client_reference_id")) {
            Long credentialId = jsonObjectParsed.get("client_reference_id").getAsLong();
            log.info("Credential id of agent = {}", credentialId);
            return credentialId;
        }
        return null;
    }

    private AgentTier extractTier(@NonNull final JsonObject metadataObject) {
        final String tier = metadataObject.get("tier").getAsString();
        return AgentTier.valueOf(tier);
    }

    private String extractState(@NonNull final JsonObject metadataObject) {
        final String selectedState = metadataObject.get("selected_state").getAsString();
        log.info("Selected State: {}", selectedState);

        return selectedState;
    }

    private List<String> extractCounties(@NonNull final JsonObject metadataObject) {
        final String countyArrayAsString = metadataObject.get("selected_counties").getAsString();
        final List<String> selectedCounties = new JSONArray(countyArrayAsString)
                .toList()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        log.info("Selected Counties: {}", selectedCounties);

        return selectedCounties;
    }

    private String getSessionProductId(@NonNull final AgentTier agentTier) {
        if (agentTier.equals(AgentTier.BASIC)) return basicPriceId;
        if (agentTier.equals(AgentTier.STANDARD)) return standardPriceId;
        if (agentTier.equals(AgentTier.PREMIUM)) return premiumPriceId;
        throw new RuntimeException("Incorrect or invalid tier.");
    }

    private Long getSessionPriceId(@NonNull final AgentTier agentTier) {
        if (agentTier.equals(AgentTier.BASIC)) return Constants.BASIC_PRICE;
        if (agentTier.equals(AgentTier.STANDARD)) return Constants.STANDARD_PRICE;
        if (agentTier.equals(AgentTier.PREMIUM)) return Constants.PREMIUM_PRICE;
        throw new RuntimeException("Incorrect or invalid tier.");
    }
}
