package com.osho.product.service.impl;

import com.osho.product.models.Product;
import com.osho.product.repository.ProductRepository;
import com.osho.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
           throw new EntityNotFoundException("Product with id " + id + " not found");
        }
        return product.get();
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
