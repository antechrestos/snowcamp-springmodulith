package org.snowcamp.university.springmodulith.stock.domain;

import org.snowcamp.university.springmodulith.common.model.ChartreuseType;
import org.springframework.modulith.events.Externalized;

@Externalized("chartreuse-stock::#{#this.type().name().toLowerCase()}")
public record StockDecrementEvent(ChartreuseType type, int number) {
}
