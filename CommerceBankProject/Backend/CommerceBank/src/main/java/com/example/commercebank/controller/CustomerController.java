package com.example.commercebank.controller;

import com.example.commercebank.domain.Customer;
import com.example.commercebank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class CustomerController {
    private CustomerService customerService;
    public static long loggedInUserId = -1;

    @CrossOrigin
    @PostMapping("/customer")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.create(customer), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Customer customer) {
        Customer found = customerService.attemptLogin(customer.getUsername(), customer.getPassword());

        if (found != null) {
            loggedInUserId = found.getCustomer_id();
            return new ResponseEntity<>(found, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @CrossOrigin
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout() {
        loggedInUserId = -1;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/findLogin")
    public ResponseEntity<?> findLogin() {
        return new ResponseEntity<>(loggedInUserId,HttpStatus.OK);
    }

}
