package com.noah.demo.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.noah.demo.springboot.domain.Customer;
import com.noah.demo.springboot.service.CustomerService;

@RestController
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getAll();
	}

	@RequestMapping("/customers/{id}")
	public Customer getCustomer(@PathVariable Integer id) {
		return customerService.get(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/customers") 
	public void create(@RequestBody Customer customer) {
		customerService.create(customer);
	}
	@RequestMapping(method = RequestMethod.PUT, value="/customers/{id}") 
	public void update(@RequestBody Customer customer, @PathVariable Integer id) {
		customerService.update(id, customer);
	}
	@RequestMapping(method = RequestMethod.DELETE, value="/customers/{id}") 
	public void delete(@PathVariable Integer id) {
		customerService.remove(id);
	}

	

	
}
