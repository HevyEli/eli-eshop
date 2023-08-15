package org.eli.order.service;

import org.eli.order.entity.Order;
import org.eli.order.repository.OrderJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
        return orderJpaRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("\"Could not find order")));
    }
    public Order createNewOrder(Order order) {
        return orderJpaRepository.save(order);
    }


    public Order deleteOrderById(long id) {
        return orderJpaRepository.delete(id);
    }
}
