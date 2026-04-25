package com.favorite.payee.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favorite.payee.customer.dto.CustomerRequest;
import com.favorite.payee.customer.dto.CustomerResponse;
import com.favorite.payee.customer.dto.LoginRequest;
import com.favorite.payee.customer.service.CustomerService;

//import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(Httpst.CREATED)
    public CustomerResponse create( @RequestBody CustomerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/internal/{id}/exists")
    public Map<String, Boolean> exists(@PathVariable Long id) {
        return Map.of("exists", service.exists(id));
    }
}