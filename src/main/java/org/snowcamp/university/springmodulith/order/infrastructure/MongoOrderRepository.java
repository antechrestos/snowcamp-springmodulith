package org.snowcamp.university.springmodulith.order.infrastructure;

import org.snowcamp.university.springmodulith.order.domain.Order;
import org.snowcamp.university.springmodulith.order.domain.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MongoOrderRepository implements OrderRepository {

    private final MongoOrderRepositoryStore store;

    private final MongoOrderMapper mapper;

    MongoOrderRepository(MongoOrderRepositoryStore store, MongoOrderMapper mapper) {
        this.store = store;
        this.mapper = mapper;
    }

    @Override
    public Order initOrder(Order order) {
        return mapper.toDomain(
                store.save(
                        mapper.fromDomain(order)
                )
        );
    }

    @Override
    public Optional<Order> getOrder(String id) {
        return store.findById(id).map(this.mapper::toDomain);
    }

    @Override
    public List<Order> listOrders() {
        return store.findAll().stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public Order saveOrder(Order order) {
        return this.mapper.toDomain(store.save(this.mapper.fromDomain(order)));
    }

    @Override
    public void deleteOrder(String id) {
        this.store.deleteById(id);
    }
}
