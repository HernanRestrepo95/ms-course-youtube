package academy.digitallab.store.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import academy.digitallab.store.product.repository.entity.Category;
import academy.digitallab.store.product.repository.entity.Product;
import academy.digitallab.store.product.service.ProductService;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> listProducts(@RequestParam(name = "categoryId", required = false) Long categoryId){

		List<Product> lstProducts;
		if(categoryId == null) {
			lstProducts = productService.listAllProducts();
			
			if(lstProducts.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
		}else {
			lstProducts = productService.findByCategory(Category.builder().id(categoryId).build());
			
			if(lstProducts.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
		}
		
		return ResponseEntity.ok(lstProducts);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
		
		Product product = productService.getProduct(id);
		
		if(product == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(product);
		}		
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
		}
		
		Product product2 = productService.createProduct(product);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(product2);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
		product.setId(id);
		
		Product product2 = productService.updateProduct(product);
		
		if(product2 == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(product2);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
		
		Product product = productService.deleteProduct(id);
		
		if(product == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(product);
		}
	}
	
	@PutMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity){

		Product product2 = productService.updateStock(id, quantity);
		
		if(product2 == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(product2);
		}
	}
	
	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
				.map(err -> {
					Map<String, String> error = new HashMap<>();
					
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		
		ErrorMessage errorMessage = ErrorMessage.builder()
				.code("01")
				.messages(errors).build();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = "";
		
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
}
