package com.example.demo.Factory;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    @Override
    public String getRole() {
        return "Admin";
    }
}
