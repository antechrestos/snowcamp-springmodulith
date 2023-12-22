package org.snowcamp.university.springmodulith.greeting.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GreeterService {


    private static final Logger LOGGER = LoggerFactory.getLogger(GreeterService.class);

    private final GreeterClient client;

    GreeterService(GreeterClient client) {
        this.client = client;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void greet(String orderId) {
        LOGGER.debug("greet - {}", orderId);
        try {
            client.greet(orderId);
            LOGGER.debug("greet complete - {}", orderId);
        } catch (RuntimeException ex) {
            LOGGER.error("greet error - {}", orderId, ex);
            throw ex;
        }
    }
}
