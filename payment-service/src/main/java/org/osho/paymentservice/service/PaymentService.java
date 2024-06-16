package org.osho.paymentservice.service;

import org.osho.paymentservice.dtos.InitiatePaymentDto;

public interface PaymentService {
    String initiatePayment(String gatewayType,String orderId,String userId);
}
