package com.example.demo.Controllers;

import com.example.demo.Domain.OrderResponse;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Service.CartService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/purchase")
    public ResponseEntity purchase() {
        cartService.makePayment(cartService.getCartCost(cartRepository.findByUserId(userService.getCurrentUserID()).getId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeStrategy/{id}")
    public ResponseEntity changeStrategy(@PathVariable Integer id) {
        cartService.changeStrategy(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/startPurchase")
    public ResponseEntity startPurchase() {
        cartService.startPurchase();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/next")
    public ResponseEntity next() {
        cartService.changeState();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders() {
        return ResponseEntity.ok(cartService.getUserOrders());
    }
}
