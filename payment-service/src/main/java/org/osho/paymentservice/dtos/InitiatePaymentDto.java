package org.osho.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {
    private String gatewayType;
    private String orderId;
    private String userId;
}
