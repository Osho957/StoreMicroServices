package com.osho.product.service;

import com.osho.product.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    Product replaceProduct(Long id, Product product);
    Product createProduct(Product product);
    Product deleteProduct(Long id);
}
