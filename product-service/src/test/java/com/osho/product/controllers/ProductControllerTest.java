package com.osho.product.controllers;

import com.osho.product.Exceptions.ProductLimitReachedException;
import com.osho.product.models.Product;
import com.osho.product.service.IProductService;
import com.osho.product.service.ProductService;
import com.osho.product.service.TokenService;
import org.antlr.v4.runtime.Token;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @MockBean
    TokenService tokenService;

    @Autowired
    ProductController productController;

    @BeforeEach
    void setup(){
        Product product = new Product();
        product.setTitle("MacBook");
        product.setId(2L);
        when(productService.getProductById(any(Long.class))).thenReturn(product);
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        when(tokenService.validateToken(any(String.class))).thenReturn(true);
    }

    @Test
    void Test_WhenGetProductByIdIsCalled_ThenReturnProduct() throws ProductLimitReachedException {
        ResponseEntity<Product> response = productController.getProductById("token",2L);
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        Product product = response.getBody();
        assertEquals("MacBook", product.getTitle());
        assertEquals(2L, product.getId());
    }
    @Test
    void Test_WhenGetProductByIdIsCalled_ThenReturnUnAuthorized() throws ProductLimitReachedException {
        when(tokenService.validateToken(any(String.class))).thenReturn(false);
        ResponseEntity<Product> response = productController.getProductById("token",2L);
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    void Test_WhenGetProductByIdIsCalled_ThenReturnException() {
        when(productService.getProductById(any(Long.class)))
                .thenThrow(new RuntimeException("Something went wrong"));

        assertThrows(RuntimeException.class, () -> productController.getProductById("token",2L));
    }


    @Test
    void Test_WhenCreateProduct_Success() {
        Product productRequest = new Product();
        productRequest.setTitle("MacBook");
        ResponseEntity<Product> response = productController.createProduct(productRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        Product productResponse = response.getBody();
        assertEquals("MacBook", productResponse.getTitle());
        assertEquals(2L, productResponse.getId());
    }

    @Test
    void Test_WhenReplaceProduct_Success() {
        Product product = new Product();
        product.setTitle("VivoBook");
        product.setId(2L);
        when(productService.replaceProduct(any(Long.class), any(Product.class))).thenReturn(product);
        Product replacedProduct = productController.replaceProduct(2L, product);
        assertNotNull(replacedProduct);
        assertEquals("VivoBook", replacedProduct.getTitle());
        assertEquals(2L, replacedProduct.getId());
    }

    @Test
    void Test_WhenUpdateProduct_Success() {
        Product product = new Product();
        product.setTitle("VivoBook");
        product.setId(2L);
        when(productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(2L, product);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Product updatedProduct = response.getBody();
        assertEquals("VivoBook", updatedProduct.getTitle());
        assertEquals(2L, updatedProduct.getId());
    }

    @Test
    void Test_WhenDeleteProduct_Success() {
        Product product = new Product();
        product.setTitle("VivoBook");
        product.setId(2L);
        when(productService.deleteProduct(any(Long.class))).thenReturn(product);
        Product deletedProduct = productController.deleteProduct(2L);
        assertNotNull(deletedProduct);
        assertEquals("VivoBook", deletedProduct.getTitle());
        assertEquals(2L, deletedProduct.getId());
    }

    @Test
    void Test_WhenGetAllProduct_Success() {
        assertNotNull(productController.getAllProduct());
    }
}