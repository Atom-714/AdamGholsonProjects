package com.example.demo.Strategy;

public class PaymentContext {

    private PaymentStrategy paymentStategy;

    public PaymentContext(PaymentStrategy paymentStategy) {
        this.paymentStategy = paymentStategy;
    }

    public void setPaymentStategy(PaymentStrategy paymentStategy) {
        this.paymentStategy = paymentStategy;
    }

    public void pay(double amount) {
        paymentStategy.pay(amount);
    }
}
