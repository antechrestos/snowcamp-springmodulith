package org.snowcamp.university.springmodulith.order.domain;

public class InvalidOrderStateException extends RuntimeException {
    private String orderId;
    private OrderState currentState;
    private OrderState requiredState;

    public InvalidOrderStateException(String orderId, OrderState currentState, OrderState requiredState) {
        super("Invalid order state " + currentState + " for order " + orderId + "; should have been in " + requiredState + " state");
        this.orderId = orderId;
        this.currentState = currentState;
        this.requiredState = requiredState;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderState getCurrentState() {
        return currentState;
    }

    public OrderState getRequiredState() {
        return requiredState;
    }
}