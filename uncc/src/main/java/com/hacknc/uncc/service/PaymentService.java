package com.hacknc.uncc.service;

import com.hacknc.uncc.entity.Payment;
import com.hacknc.uncc.entity.PaymentResult;

public interface PaymentService {
    PaymentResult processPayment(Payment paymentDetails);
}
