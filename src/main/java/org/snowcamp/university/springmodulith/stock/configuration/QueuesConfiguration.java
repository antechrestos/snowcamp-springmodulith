package org.snowcamp.university.springmodulith.stock.configuration;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;

@Configuration
public class QueuesConfiguration {
    @Bean
    public Declarables outputQueuesDeclaration() {
        DirectExchange exchange = new DirectExchange("chartreuse-stock");
        List<Queue> queues = Stream.of(ChartreuseType.values())
                .map(chartreuseType -> new Queue(chartreuseType.name()))
                .toList();
        List<Binding> bindings = queues.stream()
                .map(queue -> BindingBuilder.bind(queue)
                        .to(exchange)
                        .with(queue.getName().toLowerCase())
                )
                .toList();
        return new Declarables(
                concat(
                        concat(
                                Stream.of(exchange),
                                queues.stream()
                        ),
                        bindings.stream()
                )
                        .toList()
        );
    }
}
