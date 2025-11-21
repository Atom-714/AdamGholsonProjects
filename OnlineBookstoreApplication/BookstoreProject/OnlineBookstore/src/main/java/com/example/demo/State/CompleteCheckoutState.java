package com.example.demo.State;



public class CompleteCheckoutState implements CheckoutState{


    @Override
    public void next(CheckoutContext context) {
        System.out.println("Checkout Complete");
    }

    @Override
    public void getState() {
        System.out.println("Checkout Complete");
    }
}
