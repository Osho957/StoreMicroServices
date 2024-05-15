package com.osho.product.dtos;

import lombok.Data;

@Data
public class ExceptionDto {
    private String message;
    private String errorCode;
}
