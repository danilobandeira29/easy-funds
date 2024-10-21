package com.github.danilobandeira29.easy_funds.repositories;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "merchants")
public class MerchantEntity {

    @Id
    public UUID id;

    @Column(name = "razao_social", nullable = false)
    public String razaoSocial;

    @Column(name = "cnpj", nullable = false)
    public String cnpj;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;
}
