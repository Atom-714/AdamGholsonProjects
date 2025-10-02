package com.example.commercebank.repository;

import com.example.commercebank.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByUsername(String username);
}
