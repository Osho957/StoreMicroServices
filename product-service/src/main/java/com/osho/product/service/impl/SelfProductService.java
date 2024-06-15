package com.osho.product.service.impl;

import com.osho.product.models.Product;
import com.osho.product.repository.ProductRepository;
import com.osho.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String,Object> redisTemplate;

    public SelfProductService(ProductRepository productRepository, RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS","product_" + id);
        if (product != null) {
            return product;
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
           throw new EntityNotFoundException("Product with id " + id + " not found");
        }
        product = optionalProduct.get();
        redisTemplate.opsForHash().put("PRODUCTS","product_" + id, product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new EntityNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {

/*       This has been done by Cascading in the Category model using (CascadeType.PERSIST)
        Category category = product.getCategory();
        if(category.getId() == null){
            Category savedCategory = categoryRepository.save(category);
            product.setCategory(savedCategory);
      }
 */
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
