package com.osho.product.controllers;

import com.osho.product.dtos.SearchRequestDto;
import com.osho.product.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    @PostMapping("/")
    public ResponseEntity<?> search(@RequestBody SearchRequestDto searchRequestDto) {
        // search for products
        return ResponseEntity.ok(searchService.search(searchRequestDto.getKeyword(), searchRequestDto.getPage(), searchRequestDto.getSize()));
    }
}
