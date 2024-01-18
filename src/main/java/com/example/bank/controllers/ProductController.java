package com.example.bank.controllers;

import com.example.bank.dto.product.ChangeProductDto;
import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.enums.ProductStatus;
import com.example.bank.response.product.ListOfProductsResponse;
import com.example.bank.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products/all")
    @ResponseStatus(HttpStatus.OK)
    public ListOfProductsResponse getAll() {
        return new ListOfProductsResponse(productService.getAllProducts());
    }

    @GetMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PostMapping("/api/product/post")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto create(@RequestBody @Valid ProductDto product) {
        return productService.createProduct(product);
    }

    @GetMapping("/api/products/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ListOfProductsResponse getByStatus(@PathVariable ProductStatus status) {
        return new ListOfProductsResponse(productService.getProductsByStatus(status));
    }

    @PutMapping("/api/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto changeById(@RequestBody ChangeProductDto product,
                                 @PathVariable Integer id) {
        return productService.changeProductById(product, id);
    }
}
