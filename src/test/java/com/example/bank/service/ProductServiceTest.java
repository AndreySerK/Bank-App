package com.example.bank.service;

import com.example.bank.dto.product.ChangeProductDto;
import com.example.bank.dto.product.ProductDto;
import com.example.bank.entity.Product;
import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.entity.enums.ProductStatus;
import com.example.bank.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.bank.TestUtils.createProduct;
import static com.example.bank.TestUtils.createProductDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @Test
    public void getAllProductsTest_whenFindAll_thenReturnAllProducts() throws Exception {

        List<Product> productList = new ArrayList<>();
        productList.add(createProduct(1L, 1L));
        productList.add(createProduct(2L, 2L));

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> actualList =  productService.getAllProducts();

        assertEquals(2, actualList.size());
    }

    @Test
    public void getProductByIdTest_whenGetProductById_thenReturnProductById() throws Exception {

        Product product = createProduct(1L, 1L);
        ProductDto expectedProductDto = createProductDto(1L);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductDto actualProductDto =  productService.getProductById(1);
        assertEquals(expectedProductDto, actualProductDto);
    }

    @Test
    public void createProductTest_whenCreateProduct_thenReturnNewProduct() throws Exception {

        Product product = createProduct(1L, 1L);
        ProductDto addProductDto = createProductDto(1L);

        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto actualProductDto =  productService.createProduct(addProductDto);

        assertNotNull(actualProductDto);
    }

    @Test
    public void getProductByStatusTest_whenGetProductByStatus_thenReturnProductByStatus() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct(1L, 1L));
        productList.add(createProduct(2L, 2L));


        when(productRepository.findProductsByStatus(ProductStatus.ACTIVE)).thenReturn(productList);

        List<ProductDto> productDtoList =  productService.getProductsByStatus(ProductStatus.ACTIVE);

        assertEquals(2, productDtoList.size());
    }

    @Test
    public void changeProductByIdTest_whenChangeProduct_thenReturnUpdatedProduct() throws Exception {

        Product product = createProduct(1L, 1L);

        ChangeProductDto changeProductDto = new ChangeProductDto(
                "ChangedProduct",
                ProductStatus.INACTIVE,
                CurrencyCode.CNY,
                100000,
                0.002);

        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDto updatedProductDto = productService.changeProductById(changeProductDto, 1);

        assertEquals(changeProductDto.getName(), updatedProductDto.getName());
    }

    @Test
    public void getProductByIdTest_whenFindByIdNotExistedProduct_thenReturnException() throws Exception {

        when(productRepository.findById(500)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(500));
        verify(productRepository, times(1)).findById(500);
    }

    @Test
    public void getProductsByStatusTest_whenFindByStatusNotExistedProducts_thenReturnException() throws Exception {

        when(productRepository.findProductsByStatus(ProductStatus.INACTIVE)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> productService.getProductsByStatus(ProductStatus.INACTIVE));
        verify(productRepository, times(1)).findProductsByStatus(ProductStatus.INACTIVE);
    }
}

