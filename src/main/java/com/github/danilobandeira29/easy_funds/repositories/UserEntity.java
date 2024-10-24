package com.github.danilobandeira29.easy_funds.repositories;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    public UUID id;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "cpf", nullable = false, unique = true)
    public String cpf;

    @Column(name = "password", nullable = false)
    public String password;
}
