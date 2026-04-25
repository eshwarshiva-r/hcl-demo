package com.favorite.payee.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorite.payee.customer.entity.Customer;
import com.favorite.payee.customer.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	Customer customer;
	
	public boolean validateCustomer(Integer customerId) {
		
		Optional<Customer> customer=customerRepository.findById(customerId());
	
		if(customer.isPresent()) {
			generateOTP(customer.get());
		}else {
			throw new RuntimeException("Customer not found with id: "+customerId);
		}

}
	
	public String loginInitiate(Customer customer) {
		if(customer!=null) {
			String otp=generateOTP(customer);
			validateOTP(customer, otp);
			return "Customer validated successfully!";
		}else {
			throw new RuntimeException("Customer not found with email: "+email);
		}
		
	}

	private String generateOTP(Customer customer2) {
		
		return null;
		
	}
	
	public boolean validateOTP(String newOtp) {
		// Implement OTP validation logic here
		// For example, you can compare the provided OTP with the one generated and stored for the customer
		// Return true if the OTP is valid, otherwise return false
		return true; // Placeholder return value
		
		
	}
