package com.osho.product.service;

import com.osho.product.models.Product;

import java.util.List;

public interface SearchService {

    List<Product> search(String keyword, int page, int size);
}
