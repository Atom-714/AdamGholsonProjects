package com.example.demo.Factory;

public class UserFactory {

    public static User createUser(User user) {
        if (user.getIsAdmin() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if(!user.getIsAdmin()) {
            Customer customer = new Customer();
            customer.setUsername(user.getUsername());
            customer.setPassword(user.getPassword());
            return customer;
        }
        else{
            Admin admin = new Admin();
            admin.setUsername(user.getUsername());
            admin.setPassword(user.getPassword());
            return admin;
        }
    }
}
