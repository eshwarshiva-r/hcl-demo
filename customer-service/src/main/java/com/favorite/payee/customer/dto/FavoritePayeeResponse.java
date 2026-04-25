package com.favorite.payee.customer.dto;

public record FavoritePayeeResponse(Long id, Long customerId, String accountName, String iban, String bankName) {
}