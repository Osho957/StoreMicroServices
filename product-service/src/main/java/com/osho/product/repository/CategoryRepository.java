package com.osho.product.repository;

import com.osho.product.models.Category;
import com.osho.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    void delete(Category category);

    Optional<Category> findById(Long id);

    @Override
    Category save(Category category);

}
