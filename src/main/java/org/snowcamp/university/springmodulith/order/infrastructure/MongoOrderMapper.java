package org.snowcamp.university.springmodulith.order.infrastructure;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.snowcamp.university.springmodulith.order.domain.Order;
import org.snowcamp.university.springmodulith.order.domain.OrderState;

class MongoOrderMapper {

    public MongoOrder fromDomain(Order order) {
        return new MongoOrder(
                order.orderId(),
                order.chartreuses().stream()
                        .map(chartreuseType -> MongoOrder.MongoChartreuseType.valueOf(chartreuseType.name()))
                        .toList(),
                MongoOrder.MongoOrderState.valueOf(order.state().name())
        );
    }

    public Order toDomain(MongoOrder order) {
        return new Order(
                order.orderId(),
                order.chartreuses().stream()
                        .map(chartreuseType -> ChartreuseType.valueOf(chartreuseType.name()))
                        .toList(),
                OrderState.valueOf(order.state().name())
        );
    }
}
