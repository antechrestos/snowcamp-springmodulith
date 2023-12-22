package org.snowcamp.university.springmodulith.order.infrastructure;

import org.snowcamp.university.springmodulith.order.domain.OrderIdGenerator;
import org.snowcamp.university.springmodulith.order.domain.OrderRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;

@Configuration
@EnableMongoRepositories("org.snowcamp.university.springmodulith.order.infrastructure")
public class OrderInfrastructureConfiguration {

    @Bean
    @ConditionalOnProperty(value = "order.id-generator", havingValue = "RANDOM")
    public OrderIdGenerator randomOrderIdGenerator(){
        return () -> UUID.randomUUID().toString();
    }

    @Bean
    @ConditionalOnProperty(value = "order.id-generator", havingValue = "STATIC", matchIfMissing = true)
    public OrderIdGenerator staticOrderIdGenerator(){
        return () -> "static-for-demo";
    }

    @Bean
    public OrderRepository mongoOrderRepository(MongoOrderRepositoryStore store) {
        return new MongoOrderRepository(store, new MongoOrderMapper());
    }
}
