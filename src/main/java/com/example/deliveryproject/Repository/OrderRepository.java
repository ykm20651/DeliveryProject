package com.example.deliveryproject.Repository;

import com.example.deliveryproject.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
