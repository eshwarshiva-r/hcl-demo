package com.favorite.payee.customer.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.favorite.payee.bank.exception.BusinessException;
import com.favorite.payee.bank.repository.FavoritePayeeRepository;
import com.favorite.payee.customer.dto.FavoritePayeeRequest;
import com.favorite.payee.customer.dto.FavoritePayeeResponse;
import com.favorite.payee.customer.entity.FavoritePayee;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritePayeeService {

    private static final int MAX_ACCOUNTS_PER_CUSTOMER = 20;
    private static final int PAGE_SIZE = 5;

    private static final Map<String, String> BANK_CODE_MAP = Map.of(
            "1234", "Nairobi Bank",
            "1235", "Denver Bank",
            "1236", "Moscow Bank",
            "1237", "Tokio Bank"
    );

    private  com.favorite.payee.customer.repository.FavoritePayeeRepository favoritePayeeRepository;

    public FavoritePayeeResponse add(Long customerId, FavoritePayeeRequest request) {
        if (favoritePayeeRepository.countByCustomerId(customerId) >= MAX_ACCOUNTS_PER_CUSTOMER) {
            throw new BusinessException("Maximum of 20 favorite accounts reached");
        }

        FavoritePayee payee = FavoritePayee.builder()
                .customerId(customerId)
                .accountName(request.accountName())
                .iban(request.iban())
                .bankName(resolveBankName(request.iban()))
                .build();

        return map(favoritePayeeRepository.save(payee));
    }

    public FavoritePayeeResponse update(Long customerId, Long payeeId, FavoritePayeeRequest request) {
        FavoritePayee payee = favoritePayeeRepository.findById(payeeId)
                .filter(existing -> existing.getCustomerId().equals(customerId))
                .orElseThrow(() -> new BusinessException("Favorite account not found"));

        payee.setAccountName(request.accountName());
        payee.setIban(request.iban());
        payee.setBankName(resolveBankName(request.iban()));

        return map(favoritePayeeRepository.save(payee));
    }

    public void delete(Long customerId, Long payeeId) {
        FavoritePayee payee = favoritePayeeRepository.findById(payeeId)
                .filter(existing -> existing.getCustomerId().equals(customerId))
                .orElseThrow(() -> new BusinessException("Favorite account not found"));
        favoritePayeeRepository.delete(payee);
    }

    public Page<FavoritePayeeResponse> list(Long customerId, int page) {
        return favoritePayeeRepository.findByCustomerId(customerId, PageRequest.of(page, PAGE_SIZE)).map(this::map);
    }

    private String resolveBankName(String iban) {
        if (iban.length() < 4) {
            throw new BusinessException("IBAN must contain at least 4 characters for bank code");
        }
        String code = iban.substring(0, 4);
        String bankName = BANK_CODE_MAP.get(code);
        if (bankName == null) {
            throw new BusinessException("Bank not found for code: " + code);
        }
        return bankName;
    }

    private FavoritePayeeResponse map(FavoritePayee payee) {
        return new FavoritePayeeResponse(payee.getId(), payee.getCustomerId(), payee.getAccountName(), payee.getIban(), payee.getBankName());
    }
}