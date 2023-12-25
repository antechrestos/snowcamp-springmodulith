package org.snowcamp.university.springmodulith.order;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;

import java.util.List;

public record OrderCompleteEvent(String orderId, List<ChartreuseType> chartreuseTypes) {
}
