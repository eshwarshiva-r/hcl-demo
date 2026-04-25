package com.favorite.payee.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.favorite.payee.customer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByEmail(String email);
	
	Customer findByPhoneNumber(String phoneNumber);
	
	Customer findByEmailOrPhoneNumber(String email, String phoneNumber);
	
	Optional<Customer> findById(Integer id);

}
