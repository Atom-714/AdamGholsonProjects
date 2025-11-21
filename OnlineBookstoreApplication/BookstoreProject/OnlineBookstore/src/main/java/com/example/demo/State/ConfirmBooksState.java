package com.example.demo.State;

public class ConfirmBooksState implements CheckoutState
{
    @Override
    public void next(CheckoutContext context) {
        context.setCheckoutState(new AddressState());
    }

    @Override
    public void getState() {
        System.out.println("Confirm Books State");
    }
}
