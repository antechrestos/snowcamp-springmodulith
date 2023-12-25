package org.snowcamp.university.springmodulith.stock.configuration;

import org.snowcamp.university.springmodulith.order.OrderCompleteEvent;
import org.snowcamp.university.springmodulith.stock.domain.StockNotifier;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
class StockOrderCompleteEventListener {

    private final StockNotifier notifier;

    StockOrderCompleteEventListener(StockNotifier notifier) {
        this.notifier = notifier;
    }

    @ApplicationModuleListener
    void on(OrderCompleteEvent event){
        this.notifier.notifyOrder(event.chartreuseTypes());
    }
}
