package com.github.danilobandeira29.easy_funds.repositories;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "balance", nullable = false)
    public BigDecimal balance;

    @Column(name = "owner_id", nullable = false)
    public Long ownerId;
}
