package org.snowcamp.university.springmodulith.order.domain;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;

import java.util.List;

public record Order(String orderId, List<ChartreuseType> chartreuses, OrderState state) {
}
