package com.hacknc.uncc.service;

import com.hacknc.uncc.entity.Payment;
import com.hacknc.uncc.entity.PaymentResult;

import java.util.Random;

public class MockPaymentService implements PaymentService {

    @Override
    public PaymentResult processPayment(Payment paymentDetails) {

        boolean success = new Random().nextBoolean(); // Randomly decide if the payment is successful

        //mock transaction Id
        String mockTransactionId = "MOCKTXN" + System.currentTimeMillis();
        
        PaymentResult result = new PaymentResult();
        result.setSuccessful(success);
        result.setTransactionId(mockTransactionId);
        result.setMessage(success ? "Payment processed successfully." : "Payment failed.");
        
        return result;
    }
}
