package com.github.danilobandeira29.easy_funds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsersRepository extends JpaRepository<UserEntity, Long> {

}
