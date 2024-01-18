package com.example.bank.response.product;

import com.example.bank.dto.product.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ListOfProductsResponse {

    private final List<ProductDto> products;
}
