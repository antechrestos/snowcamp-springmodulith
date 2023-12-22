package org.snowcamp.university.springmodulith.order.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.snowcamp.university.springmodulith.greeting.domain.GreeterService;
import org.snowcamp.university.springmodulith.payment.domain.InitPaymentHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class OrderManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderManager.class);
    private final OrderRepository repository;

    private final OrderIdGenerator orderIdGenerator;
    private final InitPaymentHandler initPaymentHandler;
    private final GreeterService greeter;

    public OrderManager(
            OrderRepository repository,
            OrderIdGenerator orderIdGenerator,
            InitPaymentHandler initPaymentHandler,
            GreeterService greeter
    ) {
        this.repository = repository;
        this.orderIdGenerator = orderIdGenerator;
        this.initPaymentHandler = initPaymentHandler;
        this.greeter = greeter;
    }

    @Transactional
    public Order initOrder() {
        Order order = this.repository.initOrder(new Order(
                orderIdGenerator.generateId(),
                new ArrayList<>(),
                OrderState.IN_PROCESS
        ));
        LOGGER.debug("initOrder - order {} created", order);
        return order;
    }

    @Transactional
    public List<Order> listOrders() {
        return repository.listOrders();
    }

    @Transactional
    public Order retrieveOrder(String id) {
        Order order = this.loadExistingOrder(id);
        LOGGER.debug("getOrder - {} retrieved", id);
        return order;
    }

    @Transactional
    public Order addChartreuseToOrder(String id, ChartreuseType chartreuseType) {
        Order order = this.loadExistingOrderWithRequiredState(id, OrderState.IN_PROCESS);
        Order result = this.repository.saveOrder(
                new Order(
                        order.orderId(),
                        Stream.concat(
                                order.chartreuses().stream(),
                                Stream.of(chartreuseType)
                        ).toList(),
                        order.state()
                ));
        LOGGER.debug("Added {} to order {}", chartreuseType, order);
        return result;
    }

    @Transactional
    public Order processToPayment(String id) {
        Order order = this.loadExistingOrderWithRequiredState(id, OrderState.IN_PROCESS);
        this.initPaymentHandler.initPayment(order.orderId());

        Order result = repository.saveOrder(
                new Order(
                        id,
                        order.chartreuses(),
                        OrderState.IN_PAYMENT
                ));
        LOGGER.debug("Added order {} to payment", result);
        return result;
    }

    @Transactional
    public Order paymentComplete(String id) {
        Order order = this.loadExistingOrderWithRequiredState(id, OrderState.IN_PAYMENT);
        Order result = repository.saveOrder(
                new Order(
                        id,
                        order.chartreuses(),
                        OrderState.PAYED
                ));
        this.greeter.greet(result.orderId());
        LOGGER.debug("Payment complete for order {}", result);
        return result;
    }

    @Transactional
    public void cancelOrder(String id) {
        this.repository.deleteOrder(id);
        LOGGER.debug("Order {} cancelled", id);
    }

    private Order loadExistingOrderWithRequiredState(String id, OrderState state) {
        Order order = this.loadExistingOrder(id);
        return Optional.of(order)
                .filter(o -> o.state() == state)
                .orElseThrow(() -> new InvalidOrderStateException(id, order.state(), state));
    }

    private Order loadExistingOrder(String id) {
        return this.repository.getOrder(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

}
