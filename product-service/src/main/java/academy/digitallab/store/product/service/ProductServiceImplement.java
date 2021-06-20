package academy.digitallab.store.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.repository.entity.Category;
import academy.digitallab.store.product.repository.entity.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService{
	
	private final ProductRepository productRepository;

	@Override
	public List<Product> listAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(Product product) {
		product.setStatus("CREATED");
		product.setCreatedAt(new Date());
		
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Product product2 = getProduct(product.getId());
		
		if(product2 == null) {
			return null;
		}else {
			product2.setName(product.getName());
			product2.setCategory(product.getCategory());
			product2.setDescription(product.getDescription());
			product2.setPrice(product.getPrice());
			
			return productRepository.save(product2);	
		}	
	}

	@Override
	public Product deleteProduct(Long id) {
		Product product2 = getProduct(id);
		
		if(product2 == null) {
			return null;
		}else {
			product2.setStatus("DELETED");
			return productRepository.save(product2);	
		}	
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Long id, Double quantity) {
		Product product2 = getProduct(id);
		
		if(product2 == null) {
			return null;
		}else {
			Double stock = product2.getStock() + quantity;
			product2.setStock(stock);
			
			return productRepository.save(product2);	
		}
	}

}
