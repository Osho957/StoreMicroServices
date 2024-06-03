package com.osho.product.controllers;

import com.osho.product.models.Product;
import com.osho.product.service.ProductService;
import com.osho.product.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
    Get - Getting a product
    Delete - Delete a product
    Post - Create a product
    Put - Replace a product
    Patch - Updating a product
    Get product - modify - put
 */


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @GetMapping("/")
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable(value = "id") Long id, @RequestBody Product productRequest) {
        return productService.replaceProduct(id, productRequest);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product productRequest) {
        return new ResponseEntity<>(productService.updateProduct(id, productRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable(value = "id") Long id) {
        return productService.deleteProduct(id);
    }
}
