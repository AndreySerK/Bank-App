package com.example.bank.mappers.product;

import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;

@Mapper(componentModel = "spring",imports = Timestamp.class)
public interface ProductMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);
}
