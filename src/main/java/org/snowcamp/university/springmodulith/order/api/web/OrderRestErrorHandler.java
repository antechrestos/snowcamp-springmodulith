package org.snowcamp.university.springmodulith.order.api.web;

import org.snowcamp.university.springmodulith.order.domain.InvalidOrderStateException;
import org.snowcamp.university.springmodulith.order.domain.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class OrderRestErrorHandler {
    @ExceptionHandler(InvalidOrderStateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> invalidOrderState(InvalidOrderStateException ex) {
        return Map.of(
                "error", "invalid_order_state",
                "details", Map.of(
                        "state", ex.getCurrentState().name(),
                        "expected", ex.getRequiredState().name()
                )

        );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> orderNotFound(OrderNotFoundException ex) {
        return Map.of(
                "error", "order_not_found",
                "details", Map.of(
                        "orderId", ex.getOrderId()
                )
        );
    }
}
