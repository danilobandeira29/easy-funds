package com.github.danilobandeira29.easy_funds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMerchantRepository extends JpaRepository<MerchantEntity, UUID> {
}
