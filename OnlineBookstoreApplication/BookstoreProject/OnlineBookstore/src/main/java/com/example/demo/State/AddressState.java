package com.example.demo.State;

public class AddressState implements CheckoutState {
    @Override
    public void next(CheckoutContext context) {
        context.setCheckoutState(new PaymentSelectionState());
    }
    @Override
    public void getState() {
        System.out.println("Address State");
    }
}
