package org.snowcamp.university.springmodulith.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.snowcamp.university.springmodulith.order.domain.Order;
import org.snowcamp.university.springmodulith.order.domain.OrderManager;
import org.snowcamp.university.springmodulith.order.domain.OrderRepository;
import org.snowcamp.university.springmodulith.order.domain.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.AssertablePublishedEvents;
import org.springframework.modulith.test.Scenario;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class OrderModuleIT {

    public static final String ORDER_ID = "order-id";
    @Autowired
    private OrderManager orderManager;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void clean() {
        this.orderRepository.deleteOrder(ORDER_ID);
    }

    @Test
    public void paymentComplete_should_trigger_event_paymentComplete(AssertablePublishedEvents events) {
        this.givenOrderInPayment();

        orderManager.paymentComplete(ORDER_ID);

        assertThat(events).contains(OrderCompleteEvent.class)
                .matching(event -> event.orderId().equals(ORDER_ID));
    }

    @Test
    public void order_paid_event_should_trigger_order_complete_when_treated(Scenario scenario) {
        this.givenOrderInPayment();

        scenario.publish(new OrderPaidEvent(ORDER_ID))
                .andWaitForEventOfType(OrderCompleteEvent.class)
                .matching(event -> event.orderId().equals(ORDER_ID))
                .toArrive();
    }

    private void givenOrderInPayment() {
        this.orderRepository.saveOrder(new Order(ORDER_ID, List.of(ChartreuseType.AOP), OrderState.IN_PAYMENT));
    }
}
