package com.example.demo.State;


public class CheckoutContext {
    private CheckoutState checkoutState;

    public CheckoutContext() {
        checkoutState = new ConfirmBooksState();
        checkoutState.getState();
    }

    public void setCheckoutState(CheckoutState checkoutState) {
        this.checkoutState = checkoutState;
        checkoutState.getState();
    }

    public void next() {
        checkoutState.next(this);
    }


}
