package org.eli.order.repository;
import org.eli.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderJpaRepository extends JpaRepository <Order, Long> {

    Order delete(long id);
}
