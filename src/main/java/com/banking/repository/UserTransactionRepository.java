package com.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entity.UserTransaction;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long>{

	UserTransaction findByTransactionId(String transactionId);
	
	List<UserTransaction> findTop5ByUserAccountIdIdOrderByTransactionDateDesc(Long userAccountId);
}
