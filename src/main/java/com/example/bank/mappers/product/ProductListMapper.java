package com.example.bank.mappers.product;

import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ProductListMapper {

    List<ProductDto> toDtoList(List<Product> productList);
}
