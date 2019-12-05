package com.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	Optional<UserAccount> findById(Long id);
}
