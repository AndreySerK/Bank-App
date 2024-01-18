package com.example.bank.repositories;

import com.example.bank.entity.Product;
import com.example.bank.entity.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductsByStatus(ProductStatus status);
}
