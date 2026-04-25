package com.favorite.payee.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_payees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritePayee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false, length = 20)
    private String iban;

    @Column(nullable = false)
    private String bankName;
}