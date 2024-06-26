package com.osho.product.repository;

import com.osho.product.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    void delete(Product product);

    Optional<Product> findById(Long id);


    List<Product> findAllByTitleContains(String title, Pageable pageable);


}
