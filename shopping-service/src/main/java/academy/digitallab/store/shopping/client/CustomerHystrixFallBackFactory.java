package academy.digitallab.store.shopping.client;

import org.springframework.http.ResponseEntity;

import academy.digitallab.store.shopping.model.Customer;

public class CustomerHystrixFallBackFactory implements CustomerClient{

	@Override
	public ResponseEntity<Customer> getCustomer(long id) {
		Customer customer = Customer.builder()
				.firstName("none")
				.lastName("none")
				.email("none")
				.photoUrl("none").build();
		
		return ResponseEntity.ok(customer);
	}

}
