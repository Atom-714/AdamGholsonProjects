package com.example.demo.Domain;

import com.example.demo.Factory.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Entity
@Data
@Table(name= "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Cart() {}

    public Cart(Optional<User> user) {
        this.user = user.get();
    }


}
