package org.snowcamp.university.springmodulith.greeting.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.greeting.domain.GreeterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfiguration {


    @Bean
    @ConditionalOnProperty(name = "greetings.enabled", havingValue = "true")
    public GreeterClient greeterClient() {
        Logger logger = LoggerFactory.getLogger(GreeterClient.class);
        return orderId -> {
            logger.info("Greetings {}!!", orderId);
        };
    }

    @Bean
    @ConditionalOnProperty(name = "greetings.enabled", havingValue = "false", matchIfMissing = true)
    public GreeterClient noGreeterClient() {
        Logger logger = LoggerFactory.getLogger(GreeterClient.class);
        return orderId -> {
            logger.warn("No greetings {}", orderId);
            throw new RuntimeException("No greeting !!!");
        };
    }
}
