package com.favorite.payee.customer.dto;

public record FavoritePayeeRequest(
         String accountName,
         String iban
) {
}