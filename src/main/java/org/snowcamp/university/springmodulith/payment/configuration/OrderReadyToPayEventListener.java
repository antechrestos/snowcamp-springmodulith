package org.snowcamp.university.springmodulith.payment.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.order.OrderReadyToPayEvent;
import org.snowcamp.university.springmodulith.payment.domain.InitPaymentHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class OrderReadyToPayEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReadyToPayEventListener.class);

    private final InitPaymentHandler initPaymentHandler;

    OrderReadyToPayEventListener(InitPaymentHandler initPaymentHandler) {
        this.initPaymentHandler = initPaymentHandler;
    }

    @EventListener
    @Transactional
    public void on(OrderReadyToPayEvent event){
        LOGGER.debug("Order ready to pay event: {}", event);
        initPaymentHandler.initPayment(event.orderId());
    }

}
