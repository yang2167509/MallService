package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/browse")
    public List<String> browse() {
        // TODO: Replace this with actual product data
        return Arrays.asList("Product 1", "Product 2", "Product 3");
    }
}
