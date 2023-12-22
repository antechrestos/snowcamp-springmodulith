package org.snowcamp.university.springmodulith.payment.api.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.payment.domain.PaymentHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentHandler paymentHandler;

    PaymentController(PaymentHandler paymentHandler) {
        this.paymentHandler = paymentHandler;
    }

    @PutMapping({"/api/v1/payments/{orderId}/complete"})
    public void paymentComplete(@PathVariable("orderId") String orderId) {
        this.paymentHandler.paymentComplete(orderId);
        this.LOGGER.debug("Payment complete for order {}", orderId);
    }
}
