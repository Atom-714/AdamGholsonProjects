package com.example.demo.Domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name= "orders")
public class CustomerOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="total_price")
    private double totalPrice;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL)
    private List<OrderBook> orderedBooks;
}
