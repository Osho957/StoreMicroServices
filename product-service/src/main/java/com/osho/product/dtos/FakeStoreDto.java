package com.osho.product.dtos;

import lombok.Data;

@Data
public class FakeStoreDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private RatingDto rating;
}
