package org.snowcamp.university.springmodulith.order.domain;

public class OrderNotFoundException extends RuntimeException {
    private final String orderId;

    public OrderNotFoundException(String orderId) {
        super("Order " + orderId + " not found");
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
