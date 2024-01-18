package com.example.bank.service;

import com.example.bank.dto.product.ChangeProductDto;
import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import com.example.bank.entity.enums.ProductStatus;
import com.example.bank.mappers.product.ProductListMapper;
import com.example.bank.mappers.product.ProductMapper;
import com.example.bank.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductListMapper productListMapper;


    public List<ProductDto> getAllProducts() {
        return productListMapper.toDtoList(productRepository.findAll());
    }

    public ProductDto getProductById(Integer id) {
        return productMapper
                .toDto(productRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Product with id = " + id + " not found")));
    }

    @Transactional
    public ProductDto createProduct(@Valid ProductDto dto) {

        Product newProduct = productMapper.toEntity(dto);
        productRepository.save(newProduct);
        return productMapper.toDto(newProduct);
    }

    public List<ProductDto> getProductsByStatus(ProductStatus status) {
        List<ProductDto> productDTOList = productListMapper
                .toDtoList(productRepository
                        .findProductsByStatus(status));

        if (productDTOList.isEmpty()) {
            throw new NullPointerException("There are no products with such status!");
        }

        return productDTOList;
    }

    @Transactional
    public ProductDto changeProductById(@Valid ChangeProductDto dto, Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id = " + id + " not found")
        );

        product.setStatus(dto.getStatus());
        product.setName(dto.getName());
        product.setProductLimit(dto.getProductLimit());
        product.setInterestRate(dto.getInterestRate());
        product.setCurrencyCode(dto.getCurrencyCode());

        productRepository.save(product);
        return productMapper.toDto(product);
    }
}