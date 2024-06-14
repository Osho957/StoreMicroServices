package org.osho.paymentservice.PaymentGateways;

import org.springframework.context.annotation.Configuration;


public interface PaymentGateway {

     String generatePaymentLink(String orderId, Long amount, String email, String phoneNumber);

}
