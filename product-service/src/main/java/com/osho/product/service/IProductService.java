package com.osho.product.service;

import com.osho.product.dtos.FakeStoreDto;
import com.osho.product.models.Category;
import com.osho.product.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("fakeStoreService")
//@Primary
@RequiredArgsConstructor
public class IProductService implements ProductService {

    private final RestTemplate restTemplate;

    public Product getProductById(Long id) {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreDto.class);
        return convertDtoToProduct(fakeStoreDto);
    }

    public List<Product> getAllProducts() {
        ResponseEntity<List<FakeStoreDto>> response = restTemplate.exchange( "https://fakestoreapi.com/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        List<FakeStoreDto> fakeStoreDos = response.getBody();
        if (fakeStoreDos == null) {
            return new ArrayList<>();
        }
        return fakeStoreDos.stream()
                .map(this::convertDtoToProduct)
                .collect(Collectors.toList());
    }

    public Product updateProduct(Long id, Product product){
        FakeStoreDto fakeStoreProductDto = convertProductToDto(product);
        fakeStoreProductDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + id,
                fakeStoreProductDto, FakeStoreDto.class);
        return convertDtoToProduct(fakeStoreProductDto);
    }

    public Product replaceProduct(Long id, Product product) {
        FakeStoreDto fakeStoreDto = convertProductToDto(product);
        FakeStoreDto updatedStoreDto = restTemplate.postForObject("https://fakestoreapi.com/products/" + id, fakeStoreDto, FakeStoreDto.class);
        return convertDtoToProduct(updatedStoreDto);
    }

    public Product createProduct(Product product) {
        FakeStoreDto fakeStoreDto = convertProductToDto(product);
        FakeStoreDto response = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreDto, FakeStoreDto.class);
        return convertDtoToProduct(response);
    }

    public Product deleteProduct(Long id) {
        Product product = getProductById(id);
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return product;
    }

    private Product convertDtoToProduct(FakeStoreDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        Category category = new Category();
        category.setId(0L);
        category.setTitle(dto.getCategory());
        product.setCategory(category);
        return product;
    }

    public FakeStoreDto convertProductToDto(Product product) {
        if (product == null) {
            return null;
        }
        FakeStoreDto dto = new FakeStoreDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setCategory(product.getCategory().getTitle());
        return dto;
    }

}
