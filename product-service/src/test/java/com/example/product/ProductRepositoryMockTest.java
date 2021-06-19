package com.example.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.product.repository.ProductRepository;
import com.example.product.repository.entity.Category;
import com.example.product.repository.entity.Product;

@DataJpaTest
public class ProductRepositoryMockTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void whenFindByCategory_thenReturnListProducts() {
		Product product01 = Product.builder()
				.name("computer")
				.category(
						Category.builder()
						.id(1L).build())
				.description("")
				.stock(10.0)
				.price(1240.99)
				.status("Created")
				.createdAt(new Date()).build();
		
		productRepository.save(product01);
		
		List<Product> founds = productRepository.findByCategory(product01.getCategory());
		
		Assertions.assertThat(founds.size()).isEqualTo(3);
	}
}
