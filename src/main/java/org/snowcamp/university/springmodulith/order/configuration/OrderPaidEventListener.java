package org.snowcamp.university.springmodulith.order.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.order.OrderPaidEvent;
import org.snowcamp.university.springmodulith.order.domain.OrderManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class OrderPaidEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaidEventListener.class);

    private final OrderManager orderManager;

    OrderPaidEventListener(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @EventListener
    @Transactional
    public void on(OrderPaidEvent event){
        LOGGER.debug("Order paid event: {}", event);
        orderManager.paymentComplete(event.orderId());
    }
}
