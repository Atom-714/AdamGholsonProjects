package com.example.demo.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "order_book")
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_id")
    private Integer orderId;

    @Column(name="book_id")
    private Integer bookId;
}
