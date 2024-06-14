package com.osho.product.service.impl;

import com.osho.product.models.Product;
import com.osho.product.repository.ProductRepository;
import com.osho.product.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ProductRepository productRepository;


    @Override
    public List<Product> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        return productRepository.findAllByTitleContains(keyword,pageable);
    }
}
