package org.osho.paymentservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.osho.paymentservice.PaymentGateways.PaymentGateway;
import org.osho.paymentservice.dtos.Product;
import org.osho.paymentservice.dtos.User;
import org.osho.paymentservice.enums.PaymentGatewayType;
import org.osho.paymentservice.factory.PaymentGatewayFactory;
import org.osho.paymentservice.service.PaymentService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGatewayFactory paymentGatewayFactory;

    private final RestTemplate restTemplate;

    @Override
    public String initiatePayment(String gatewayType, String orderId, String userId) {
        // get order details from product service  check if quantity is greater than 0 and then get the amount to be paid
        // then call the payment gateway to initiate the payment and return the payment link
        // update the order quantity in the product service
        Product product = restTemplate.getForObject("http://localhost:8080/orders/" + orderId, Product.class);
        User user = restTemplate.getForObject("http://localhost:8081/users/profile/" + userId, User.class);
        System.out.println(user);
        if (user == null) {
            return "User not found";
        }
        if (product == null || product.getQuantity() <= 0) {
            return "Product is out of stocks or not available";
        }
        product.setQuantity(product.getQuantity() - 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(product);
        HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);
        ResponseEntity<Product> response = restTemplate.exchange(
                "http://localhost:8080/orders/update/" + orderId,
                HttpMethod.PATCH,
                requestEntity,
                Product.class
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            return "Failed to update the order, Please try again later";
        }
        Long amountToBePaid = (long) (product.getPrice() * 100);
        String email = user.getEmail();
        String name = user.getName();
        PaymentGatewayType type = PaymentGatewayType.valueOf(gatewayType.toUpperCase());
        PaymentGateway paymentGateway = paymentGatewayFactory.getPaymentGateway(type);
        return paymentGateway.generatePaymentLink(orderId,amountToBePaid,email,name);
    }
}
