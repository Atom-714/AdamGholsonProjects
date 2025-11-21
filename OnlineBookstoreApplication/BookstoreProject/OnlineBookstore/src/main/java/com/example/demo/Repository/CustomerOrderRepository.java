package com.example.demo.Repository;

import com.example.demo.Domain.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrders, Integer> {
    List<CustomerOrders> findByUserId(Integer userId);
}
