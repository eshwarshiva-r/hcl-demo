package com.favorite.payee.customer.repository;

import java.awt.print.Pageable;

import org.hibernate.query.Page;

public interface FavoritePayeeRepository extends org.springframework.data.jpa.repository.JpaRepository<com.favorite.payee.customer.entity.FavoritePayee, Long> {
	 long countByCustomerId(Long customerId);

	    
	    
	    org.springframework.data.domain.Page<com.favorite.payee.customer.entity.FavoritePayee> findByCustomerId(Long customerId, org.springframework.data.domain.Pageable pageable);

}
