package org.osho.paymentservice.service.Impl;

import com.razorpay.PaymentLink;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.osho.paymentservice.PaymentGateways.PaymentGateway;
import org.osho.paymentservice.dtos.InitiatePaymentDto;
import org.osho.paymentservice.enums.PaymentGatewayType;
import org.osho.paymentservice.factory.PaymentGatewayFactory;
import org.osho.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGatewayFactory paymentGatewayFactory;

    @Override
    public String initiatePayment(String gatewayType,String orderId,long amount,String email,String phone) {
        PaymentGatewayType type = PaymentGatewayType.valueOf(gatewayType.toUpperCase());
        PaymentGateway paymentGateway = paymentGatewayFactory.getPaymentGateway(type);
        return paymentGateway.generatePaymentLink(orderId,amount,email,phone);
    }


}
