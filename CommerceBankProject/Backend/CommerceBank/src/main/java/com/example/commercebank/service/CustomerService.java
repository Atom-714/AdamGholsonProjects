package com.example.commercebank.service;

import com.example.commercebank.domain.Customer;
import com.example.commercebank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer attemptLogin(String username, String password) {
        Customer customer = customerRepository.findByUsername(username);

        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }

        return null;
    }
}
