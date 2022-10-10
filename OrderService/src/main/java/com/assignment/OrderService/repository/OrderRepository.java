package com.assignment.OrderService.repository;
import com.assignment.OrderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author sliyanag
 * @created 10/10/2022 - 11:20 AM
 * @project EurekaServer
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
