package org.osho.paymentservice.PaymentGateways;


import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RazorPayGateway implements PaymentGateway{

    private final RazorpayClient razorpayClient;

    @Override
    public String generatePaymentLink(String orderId, Long amount, String email, String name) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",(System.currentTimeMillis() + 86400000)/1000);
        paymentLinkRequest.put("reference_id",UUID.randomUUID()+orderId);
        paymentLinkRequest.put("description","Payment for order no #"+ orderId);
        JSONObject customer = new JSONObject();
        customer.put("name",name);
//        customer.put("contact","9999999999");
        customer.put("email",email);
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",false);
        JSONObject notes = new JSONObject();
        notes.put("Note: ","payment for order no #"+orderId);
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");
        PaymentLink payment = null;
        try {
             payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        }catch (Exception e){
            throw new RuntimeException("Payment link generation failed " + e.getMessage());
        }

        return payment.get("short_url").toString();
    }

}
