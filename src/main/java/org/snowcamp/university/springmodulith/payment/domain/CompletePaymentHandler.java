package org.snowcamp.university.springmodulith.payment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.order.domain.OrderManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CompletePaymentHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletePaymentHandler.class);
    private final OrderManager orderManager;

    public CompletePaymentHandler(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @Transactional
    public void paymentComplete(String orderId) {
        LOGGER.debug("Payment complete {}", orderId);
        orderManager.paymentComplete(orderId);
    }
}
