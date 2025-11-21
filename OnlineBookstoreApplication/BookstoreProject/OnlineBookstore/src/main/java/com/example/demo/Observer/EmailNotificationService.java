package com.example.demo.Observer;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements Observer{
    @Override
    public void update(String message) {
        System.out.println("Sending email: " + message);
    }
}
