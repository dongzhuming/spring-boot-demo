package com.noah.demo.springboot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noah.demo.springboot.domain.Customer;
import com.noah.demo.springboot.domain.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository repo;

	@Autowired
	public CustomerService(final CustomerRepository repo) {
		this.repo = repo;
	}

	private List<Customer> customers = new ArrayList<>(Arrays.asList(new Customer(1, "张三"), new Customer(2, "李四")));

	public List<Customer> getAll() {
		return repo.findAll();
	}

	public Customer get(Integer id) {
		return repo.getOne(id);
	}

	public void create(Customer customer) {
		repo.save(customer);
	}

	public void update(Integer id, Customer customer) {
		customer.setId(id);
		repo.save(customer);
	}

	public void remove(Integer id) {
		repo.delete(id);
	}
}
