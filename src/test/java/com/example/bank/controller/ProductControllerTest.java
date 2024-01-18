package com.example.bank.controller;

import com.example.bank.TestUtils;
import com.example.bank.dto.product.ProductDto;
import com.example.bank.dto.product.ChangeProductDto;
import com.example.bank.entity.enums.ProductStatus;
import com.example.bank.entity.enums.CurrencyCode;
import com.example.bank.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.bank.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest  {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getAllTest_whenFindAll_thenReturnAllProducts() throws Exception {

        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(createProductDto(1L));
        productDtoList.add(createProductDto(2L));


        Mockito.when(productService.getAllProducts()).thenReturn(productDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/products/all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/product/getAllProductsResponse.json");

        Mockito.verify(productService, Mockito.times(1)).getAllProducts();

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenGetProductById_thenReturnProductById() throws Exception {

        ProductDto productDto = createProductDto(1L);

        Mockito.when(productService.getProductById(1)).thenReturn(productDto);

        String actualResponse = mockMvc.perform(get("/auth/api/product/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/product/getProductByIdResponse.json");

        Mockito.verify(productService, Mockito.times(1)).getProductById(1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByStatusTest_whenGetProductByStatus_thenReturnProductByStatus() throws Exception {

        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(createProductDto(1L));
        productDtoList.add(createProductDto(2L));


        Mockito.when(productService.getProductsByStatus(ProductStatus.ACTIVE)).thenReturn(productDtoList);

        String actualResponse = mockMvc.perform(get("/auth/api/products/ACTIVE"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/product/getProductsByStatusResponse.json");

        Mockito.verify(productService, Mockito.times(1)).getProductsByStatus(ProductStatus.ACTIVE);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateProduct_thenReturnNewProduct() throws Exception {

        ProductDto addProductDto = createProductDto(1L);

        Mockito.when(productService.createProduct(addProductDto)).thenReturn(addProductDto);

        String actualResponse = mockMvc.perform(post("/auth/api/product/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addProductDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/product/createProductResponse.json");

        Mockito.verify(productService, Mockito.times(1)).createProduct(addProductDto);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void changeByIdTest_whenChangeProduct_thenReturnUpdatedProduct() throws Exception {

        ChangeProductDto changeProductDto = new ChangeProductDto(
                "ChangedProduct",
                ProductStatus.INACTIVE,
                CurrencyCode.CNY,
                100000,
                0.002);
        ProductDto updatedProduct = TestUtils.getChangedProductDto(1L, changeProductDto);

        Mockito.when(productService.changeProductById(changeProductDto, 1)).thenReturn(updatedProduct);

        String actualResponse = mockMvc.perform(put("/auth/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeProductDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = TestUtils.readStringFromResource("/response/product/changeProductResponse.json");

        Mockito.verify(productService, Mockito.times(1)).changeProductById(changeProductDto, 1);

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void getByIdTest_whenFindByIdNotExistedProduct_thenReturnError() throws Exception {

        Mockito.when(productService.getProductById(500)).thenThrow(new EntityNotFoundException("Product with id = 500 not found"));

        var actualResponse = mockMvc.perform(get("/auth/api/product/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/product/productByIdNotFoundResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void createTest_whenCreateProductWithEmptyName_thenReturnValidationErrorResponse() throws Exception {
        ProductDto newProduct = new ProductDto(
                null,
                ProductStatus.ACTIVE,
                CurrencyCode.CNY,
                10000,
                0.003,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()));

        var actualResponse = mockMvc.perform(post("/auth/api/product/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        String expectedResponse = TestUtils.readStringFromResource("/response/product/createProductWithErrorsResponse.json");

        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }
}
