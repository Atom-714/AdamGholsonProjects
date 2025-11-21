package com.example.demo.Service;

import com.example.demo.Domain.Cart;
import com.example.demo.Factory.User;
import com.example.demo.Factory.UserFactory;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    private UserFactory userFactory = new UserFactory();

    @Getter
    @Setter
    private int currentUserID = 0;

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + user.getUsername());
        }
        return userRepository.save(userFactory.createUser(user));
    }

    public void login(User user) {
        Optional<User> temp = userRepository.findByUsername(user.getUsername());
        if (temp.isPresent() && temp.get().getPassword().equals(user.getPassword())) {
            currentUserID = temp.get().getId();
        }
        else {
            throw new IllegalArgumentException("User not found: " + user.getUsername());
        }
    }

    public long getUserCartID() {
        if (currentUserID == 0) {
            throw new IllegalArgumentException("User not logged in");
        }
        return cartRepository.findByUser(userRepository.findById(currentUserID)).getId();
    }
}
