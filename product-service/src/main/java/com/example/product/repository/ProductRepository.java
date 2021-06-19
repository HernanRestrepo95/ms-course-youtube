package com.example.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.repository.entity.Category;
import com.example.product.repository.entity.Product;


@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

   public List<Product> findByCategory(Category category);
}