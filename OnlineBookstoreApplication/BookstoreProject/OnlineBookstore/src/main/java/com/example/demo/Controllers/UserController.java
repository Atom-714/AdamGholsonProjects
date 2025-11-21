package com.example.demo.Controllers;

import com.example.demo.Domain.Book;
import com.example.demo.Domain.Cart;
import com.example.demo.Factory.User;
import com.example.demo.Factory.UserFactory;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CartService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;


    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }

    @GetMapping("/current")
    public ResponseEntity<Optional<User>> getCurrentUser() {
        int id = userService.getCurrentUserID();
        if (id == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userRepository.findById(userService.getCurrentUserID()));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        userService.createUser(user);
        cartService.getOrCreateCartForUser(user.getUsername());
        userService.login(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        userService.login(user);
        return ResponseEntity.ok("Login successful!");
    }
}
