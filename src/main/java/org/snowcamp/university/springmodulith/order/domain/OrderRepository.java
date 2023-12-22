package org.snowcamp.university.springmodulith.order.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order initOrder(Order order);

    Optional<Order> getOrder(String id);

    List<Order> listOrders();

    Order saveOrder(Order order);

    void deleteOrder(String id);
}
