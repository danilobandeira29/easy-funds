package com.github.danilobandeira29.easy_funds.repositories;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity {
    @Id
    private UUID id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
}
