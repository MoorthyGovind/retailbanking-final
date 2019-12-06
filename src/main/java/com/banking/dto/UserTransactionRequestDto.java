package com.banking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @author  AKUTHOTA.RAGHU 
 * @version 1.0
 * @since   2019-12-05
 * Here, Constants - UserTransactionRequestDto for input values
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTransactionRequestDto {

	private String payeeName;
	private Long payeeAccountNumber;
	private String transactionType;
	private LocalDate transactionDate;
	private Double transactionAmount;

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Long getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(Long payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
}
