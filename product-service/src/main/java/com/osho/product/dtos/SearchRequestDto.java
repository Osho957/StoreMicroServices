package com.osho.product.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {

    private String keyword;
    private int page;
    private int size;
}
