package org.snowcamp.university.springmodulith.payment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.order.domain.OrderManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PaymentHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentHandler.class);
    private final OrderManager orderManager;

    public PaymentHandler(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void initPayment(String orderId) {
        LOGGER.info("Init payment {}", orderId);
    }

    @Transactional
    public void paymentComplete(String orderId) {
        LOGGER.debug("Payment complete {}", orderId);
        orderManager.paymentComplete(orderId);
    }
}
