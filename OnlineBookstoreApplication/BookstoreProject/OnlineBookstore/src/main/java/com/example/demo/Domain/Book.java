package com.example.demo.Domain;

import lombok.*;
import jakarta.persistence.*;

@Data
@Entity
@Table(name= "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private Double price;

    @Column(name = "available")
    private boolean available = true;

    @Column(name = "cartID")
    private Integer cartId;

    @Column(name = "userID")
    private Integer userId;
}
