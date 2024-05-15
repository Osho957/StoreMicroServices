package com.osho.product.Exceptions;

public class ProductLimitReachedException extends Exception {
    public ProductLimitReachedException(String s) {
        super(s);
    }
}
