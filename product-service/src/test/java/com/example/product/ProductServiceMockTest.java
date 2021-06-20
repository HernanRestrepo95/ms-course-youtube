package com.example.product;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.repository.entity.Category;
import academy.digitallab.store.product.repository.entity.Product;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.ProductServiceImplement;

@SpringBootTest
public class ProductServiceMockTest {

	@Mock
	private ProductRepository productRepository;
	
	private ProductService productService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		productService = new ProductServiceImplement(productRepository);
		
		Product product01 = Product.builder()
				.name("computer")
				.category(
						Category.builder()
						.id(1L).build())
				.stock(5.0)
				.price(12.5).build();
		
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product01));
		
		Mockito.when(productRepository.save(product01)).thenReturn(product01);
	}
	
	@Test
	public void whenValidGetID_ThenReturnProduct() {
		Product found = productService.getProduct(1L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");
	}
	
	@Test
	public void whenValidUpdateStock_ThenReturnNewStock() {
		Product newStock = productService.updateStock(1L, 8.0);
		
		Assertions.assertThat(newStock.getStock()).isEqualTo(13);
	}
}
