package org.eli.order.service;

import org.eli.order.entity.Order;
import org.eli.order.repository.OrderJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderJpaRepository orderJpaRepository;

    public OrderService(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    public List<Order> getAllOrders() {
        return orderJpaRepository.findAll();
    }

    public Order getOrderById(long id) {
        return orderJpaRepository.findById(id).orElseThrow(() -> new IllegalStateException("\"Could not find order"));
    }
    public Order createNewOrder(Order order) {
        return orderJpaRepository.save(order);
    }


    public void deleteOrderById(long id) {
        orderJpaRepository.deleteById(id);
    }
}
