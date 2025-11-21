package com.example.demo.Repository;

import com.example.demo.Domain.Cart;
import com.example.demo.Factory.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(Optional<User> user);
    Cart findByUserId(int userId);
}
