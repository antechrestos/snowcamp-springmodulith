package org.snowcamp.university.springmodulith.greeting.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.greeting.domain.GreeterService;
import org.snowcamp.university.springmodulith.order.OrderCompleteEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
class OrderCompleteEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCompleteEventListener.class);
    private final GreeterService greeterService;

    OrderCompleteEventListener(GreeterService greeterService) {
        this.greeterService = greeterService;
    }

    @ApplicationModuleListener
    void on(OrderCompleteEvent event){
        LOGGER.debug("Order complete event: {}", event);
        greeterService.greet(event.orderId());
    }
}
