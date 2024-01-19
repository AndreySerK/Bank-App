package com.example.bank.mapper;

import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import com.example.bank.mappers.product.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.bank.TestUtils.createProduct;
import static com.example.bank.TestUtils.createProductDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    Product product;
    ProductDto productDto;

    @BeforeEach
    void setup() {
        product = createProduct(1L,1L);
        productDto = createProductDto(1L);
    }

    @Test
    public void toDtoTest_whenGetProduct_shouldReturnProductDto () {

        ProductDto actualProductDto = productMapper.toDto(product);

        assertEquals(productDto.getStatus(), actualProductDto.getStatus());
        assertEquals(productDto.getProductLimit(), actualProductDto.getProductLimit());
        assertEquals(productDto.getInterestRate(), actualProductDto.getInterestRate());
        assertEquals(productDto.getName(), actualProductDto.getName());
        assertEquals(productDto.getCurrencyCode(), actualProductDto.getCurrencyCode());
    }

    @Test
    public void toEntityTest_whenGetProductDto_shouldReturnProduct () {

        Product actualProduct = productMapper.toEntity(productDto);


        assertEquals(product.getStatus(), actualProduct.getStatus());
        assertEquals(product.getProductLimit(), actualProduct.getProductLimit());
        assertEquals(product.getInterestRate(), actualProduct.getInterestRate());
        assertEquals(product.getName(), actualProduct.getName());
        assertEquals(product.getCurrencyCode(), actualProduct.getCurrencyCode());
    }
}
