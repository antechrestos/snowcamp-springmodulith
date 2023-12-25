package org.snowcamp.university.springmodulith.stock.domain;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StockNotifier {

    private final ApplicationEventPublisher eventPublisher;

    StockNotifier(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notifyOrder(List<ChartreuseType> chartreuseTypes){
        chartreuseTypes.stream().collect(Collectors.groupingBy(Function.identity()))
                .entrySet().stream()
                .map(entry -> new StockDecrementEvent(entry.getKey(), entry.getValue().size()))
                .forEach(eventPublisher::publishEvent);
    }
}
