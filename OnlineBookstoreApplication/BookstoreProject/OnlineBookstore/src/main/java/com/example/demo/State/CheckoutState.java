package com.example.demo.State;

public interface CheckoutState {
    void next(CheckoutContext context);
    void getState();
}
