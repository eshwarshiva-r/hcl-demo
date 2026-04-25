package com.favorite.payee.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.favorite.payee.customer.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);

}
