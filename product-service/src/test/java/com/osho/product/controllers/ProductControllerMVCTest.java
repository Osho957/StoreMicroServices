//package com.osho.product.controllers;
//
//import com.osho.product.Exceptions.ProductLimitReachedException;
//import com.osho.product.models.Product;
//import com.osho.product.service.ProductService;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.when;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ProductController.class)
//public class ProductControllerMVCTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    ProductService productService;
//
//    @Test
//    void testGetProductById() throws Exception {
//        Product product = new Product();
//        product.setId(1L);
//        product.setTitle("MacBook");
//        product.setPrice(1000.0);
//        product.setDescription("Apple MacBook");
//        when(productService.getProductById(1L)).thenReturn(product);
//        mockMvc.perform(get("/products/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isFound())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("MacBook"))
//                .andExpect(jsonPath("$.price").value(1000.0))
//                .andExpect(jsonPath("$.description").value("Apple MacBook"));
//    }
//
//    @Test
//    void testGetProductById_Negative() throws Exception {
//        when(productService.getProductById(1L)).thenThrow(new RuntimeException("Something went wrong"));
//        mockMvc.perform(get("/products/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testGetAllProduct() throws Exception {
//        mockMvc.perform(get("/products/").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//}
