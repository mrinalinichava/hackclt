package com.hacknc.uncc.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResult {
    private boolean successful;
    private String transactionId;
    private String message;
}
