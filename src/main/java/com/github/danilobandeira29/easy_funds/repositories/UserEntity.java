package com.github.danilobandeira29.easy_funds.repositories;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "full_name", nullable = false)
    public String fullName;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "cpf", nullable = false)
    public String cpf;

    @Column(name = "password", nullable = false)
    public String password;
}
