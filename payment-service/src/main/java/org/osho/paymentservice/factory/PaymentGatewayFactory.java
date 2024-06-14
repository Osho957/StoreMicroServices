package org.osho.paymentservice.factory;

import lombok.RequiredArgsConstructor;
import org.osho.paymentservice.PaymentGateways.PaymentGateway;
import org.osho.paymentservice.PaymentGateways.RazorPayGateway;
import org.osho.paymentservice.PaymentGateways.StripePaymentGateway;
import org.osho.paymentservice.enums.PaymentGatewayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayFactory {

    private final StripePaymentGateway stripePaymentGateway;

    private final RazorPayGateway razorPayGateway;

    public PaymentGateway getPaymentGateway(PaymentGatewayType type) {
        return switch (type) {
            case STRIPE -> stripePaymentGateway;
            case RAZORPAY -> razorPayGateway;

        };
    }
}
