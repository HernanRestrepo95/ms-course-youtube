package academy.digitallab.store.shopping.model;

import java.util.Date;

import lombok.Data;

@Data
public class Product {
	private Long id;
	private String name;
	private Double stock;
	private Date createdAt;
	private Category category;
	private Double price;
	private String status;
	private String description;	
}
