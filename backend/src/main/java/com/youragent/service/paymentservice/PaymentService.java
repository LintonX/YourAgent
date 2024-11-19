package com.youragent.service.paymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import com.youragent.model.Agent;
import lombok.NonNull;

public interface PaymentService {

    public String createStripePaymentSession(@NonNull final Agent agent) throws StripeException, JsonProcessingException;
}
