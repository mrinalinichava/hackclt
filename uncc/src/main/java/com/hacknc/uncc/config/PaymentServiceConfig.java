package com.hacknc.uncc.config;

import com.hacknc.uncc.service.MockPaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class PaymentServiceConfig {

    @Bean
    @Profile("dev")
    public MockPaymentService paymentService() {
        return new MockPaymentService();
    }
}
