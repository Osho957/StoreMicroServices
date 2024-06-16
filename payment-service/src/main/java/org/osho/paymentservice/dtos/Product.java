package org.osho.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private String title;
    private double price;
    private Category category;
    private String description;
    private String image;
    private int quantity;
}
