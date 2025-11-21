package com.example.demo.Strategy;

public class GiftCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " with Gift Card.");
    }
}
