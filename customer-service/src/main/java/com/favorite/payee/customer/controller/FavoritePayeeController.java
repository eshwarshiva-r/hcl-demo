package com.favorite.payee.customer.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.favorite.payee.customer.dto.FavoritePayeeRequest;
import com.favorite.payee.customer.dto.FavoritePayeeResponse;
import com.favorite.payee.customer.service.FavoritePayeeService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/customers/{customerId}/favorite-payees")
@RequiredArgsConstructor
public class FavoritePayeeController {

    private FavoritePayeeService favoritePayeeService;

    @PostMapping
    public FavoritePayeeResponse add(@PathVariable Long customerId, @Validated @RequestBody FavoritePayeeRequest request) {
        return favoritePayeeService.add(customerId, request);
    }

    @PutMapping("/{payeeId}")
    public FavoritePayeeResponse update(
            @PathVariable Long customerId,
            @PathVariable Long payeeId,
            @Validated @RequestBody FavoritePayeeRequest request
    ) {
        return favoritePayeeService.update(customerId, payeeId, request);
    }

    @DeleteMapping("/{payeeId}")
    public void delete(@PathVariable Long customerId, @PathVariable Long payeeId) {
        favoritePayeeService.delete(customerId, payeeId);
    }

    @GetMapping
    public Page<FavoritePayeeResponse> list(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return favoritePayeeService.list(customerId, page);
    }
}