package com.example.demo.Domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Double totalPrice;
    private List<Book> books;
}