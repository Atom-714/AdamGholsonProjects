package com.example.demo.Repository;

import com.example.demo.Domain.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    List<OrderBook> findByOrderId(Integer orderId);
}
