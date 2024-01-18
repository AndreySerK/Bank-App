package com.example.bank.mapper;

import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import com.example.bank.mappers.product.ProductListMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductListMapperTest {

    @Autowired
    ProductListMapper productListMapper;

    @Test
    public void toDtoListTest_whenGetProductList_shouldReturnProductDtoList () {
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct(1L, 1L));

        List<ProductDto> expectedProductDtoList = new ArrayList<>();
        expectedProductDtoList.add(createProductDto(1L));

        List<ProductDto> actualProductDtoList = productListMapper.toDtoList(productList);

        assertEquals(expectedProductDtoList, actualProductDtoList);
    }
}
