package org.osho.paymentservice.PaymentGateways;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway{

    @Value("${stripe.key.secret}")
    private String stripekey;

    @Override
    public String generatePaymentLink(String orderId, Long amount, String email,String name) {
        Stripe.apiKey = stripekey;

        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("INR")
                        .setUnitAmount(amount)
                        .setProductData(PriceCreateParams.ProductData.builder()
                                .setName("Gold Plan").build()
                        )
                        .build();

        Price price = null;
        try {
            price = Price
                    .create(priceCreateParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build())
                        .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                        .setUrl("https://www.google.com")
                                        .build())
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .build()).build();
        PaymentLink paymentLink = null;
        try {
            paymentLink = PaymentLink.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        return paymentLink.getUrl();
    }

}
