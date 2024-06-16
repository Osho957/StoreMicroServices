package org.osho.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.osho.paymentservice.dtos.InitiatePaymentDto;
import org.osho.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody InitiatePaymentDto initiatePaymentDto) {
        /*
           Get the order details from the order service
           and then get the amount to be paid
           and then call the payment service to initiate the payment
         */
        return paymentService.initiatePayment(initiatePaymentDto.getGatewayType(),initiatePaymentDto.getOrderId(), initiatePaymentDto.getUserId());
    }
}
