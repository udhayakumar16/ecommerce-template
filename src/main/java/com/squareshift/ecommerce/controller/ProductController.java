package com.squareshift.ecommerce.controller;

import com.squareshift.ecommerce.dto.ProductDto;
import com.squareshift.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class    ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/v1/product/{id}")
    public ProductDto getProductById(@PathVariable Long id) throws Exception{
        return productService.getProductById(id);
    }
}
