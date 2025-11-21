package com.example.demo.State;

public class PaymentSelectionState implements CheckoutState {

    @Override
    public void next(CheckoutContext context) {
        context.setCheckoutState(new CompleteCheckoutState());
    }

    @Override
    public void getState() {
        System.out.println("Payment Selection State");
    }
}
