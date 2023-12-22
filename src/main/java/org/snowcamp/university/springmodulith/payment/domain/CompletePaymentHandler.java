package org.snowcamp.university.springmodulith.payment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.order.OrderPaidEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CompletePaymentHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletePaymentHandler.class);
    private final ApplicationEventPublisher eventPublisher;

    CompletePaymentHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @Transactional
    public void paymentComplete(String orderId) {
        LOGGER.debug("Payment complete {}", orderId);
        eventPublisher.publishEvent(new OrderPaidEvent(orderId));
    }
}
