package com.springbootCohort.Module3.controllers;

import com.springbootCohort.Module3.entities.ProductEntity;
import com.springbootCohort.Module3.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final int PAGE_SIZE = 5;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProductsOrderByPrice(@RequestParam(defaultValue = "id") String sortBy) {
        //  return productRepository.findByOrderByPrice();
        //  for sorting with different fields, we need different methods to be created. So, we have a better solution

        return productRepository.findBy(Sort.by(sortBy));

        //  to pass the ascending / descending factor, we need to pass like this
        //  return productRepository.findBy(Sort.by(Sort.Direction.ASC, sortBy));

        //  to add tie-breaker, we need to add the fields after the sortBy. We can add as many tie-breakers as we want
        //  return productRepository.findBy(Sort.by(Sort.Direction.DESC, sortBy, "title", "sku"));
    }

    @GetMapping("/pages")
    public List<ProductEntity> learnPagination(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sortBy));
        return productRepository.findAll(pageable).getContent();
    }
}
