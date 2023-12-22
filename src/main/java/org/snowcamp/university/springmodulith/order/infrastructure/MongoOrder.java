package org.snowcamp.university.springmodulith.order.infrastructure;

import org.springframework.data.annotation.Id;

import java.util.List;

public record MongoOrder(@Id String orderId, List<MongoChartreuseType> chartreuses, MongoOrderState state) {
    public enum MongoChartreuseType {
        AOP, GREEN, YELLOW
    }

    public enum MongoOrderState {
        IN_PROCESS, IN_PAYMENT, PAYED, SENT
    }
}
