package com.example.bank.repository;

import com.example.bank.entity.Product;
import com.example.bank.entity.enums.ProductStatus;
import com.example.bank.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void findProductByIdTest_whenFoundProductById_thenReturnProductById() throws Exception {

        Product product = createProduct(1L, 1L);

        productRepository.save(product);

        Product productDB = productRepository.findById(1).get();

        assertThat(productDB).isNotNull();
        assertEquals(product.getName(), productDB.getName());
    }

    @Test
    public void findProductsByStatusTest_whenFoundProductsByStatus_thenReturnProductsByStatus() throws Exception {

        List<Product> actualList = productRepository.findProductsByStatus(ProductStatus.ACTIVE);

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }

    @Test
    public void findAllProductsTest_whenFoundAllProducts_thenReturnAllProducts() throws Exception {

        List<Product> actualList = productRepository.findAll();

        assertThat(actualList).isNotNull();
        assertEquals(1, actualList.size());
    }
}