package org.snowcamp.university.springmodulith.order.api.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.snowcamp.university.springmodulith.order.domain.Order;
import org.snowcamp.university.springmodulith.order.domain.OrderManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderManager orderManager;

    OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PostMapping({"/api/v1/orders"})
    @ResponseStatus(HttpStatus.CREATED)
    public Order initOrder() {
        Order result = this.orderManager.initOrder();
        LOGGER.debug("Init order with id {}", result.orderId());
        return result;
    }

    @GetMapping({"/api/v1/orders/{orderId}"})
    @ResponseStatus(HttpStatus.OK)
    public Order getOrder(@PathVariable("orderId") String orderId) {
        Order result = this.orderManager.retrieveOrder(orderId);
        LOGGER.debug("Retrieved order {}", result.orderId());
        return result;
    }

    @GetMapping({"/api/v1/orders/"})
    @ResponseStatus(HttpStatus.OK)
    public List<Order> listOrders() {
        return orderManager.listOrders();
    }

    @DeleteMapping({"/api/v1/orders/{orderId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable("orderId") String orderId) {
        this.orderManager.cancelOrder(orderId);
        LOGGER.debug("Cancelled order {}", orderId);
    }

    @PutMapping({"/api/v1/orders/{orderId}/chartreuses/{type}"})
    public Order addChartreuse(
            @PathVariable("orderId")
            String orderId,
            @PathVariable("type")
            @Parameter(schema = @Schema(allowableValues = {"AOP", "GREEN", "YELLOW"}))
            String type) {
        Order result = this.orderManager.addChartreuseToOrder(orderId, ChartreuseType.valueOf(type));
        LOGGER.debug("Added {} to order {}", type, orderId);
        return result;
    }

    @PutMapping({"/api/v1/orders/{orderId}/state/in_payment"})
    public Order moveToPayment(@PathVariable("orderId") String orderId) {
        Order result = this.orderManager.processToPayment(orderId);
        LOGGER.debug("Order {} moved to payment", orderId);
        return result;
    }


}
