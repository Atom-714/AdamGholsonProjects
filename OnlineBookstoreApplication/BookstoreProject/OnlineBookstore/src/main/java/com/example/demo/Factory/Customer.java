package com.example.demo.Factory;

import com.example.demo.Domain.Cart;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("Customer")
public class Customer extends User{




    @Override
    public String getRole() {
        return "Customer";
    }
}
