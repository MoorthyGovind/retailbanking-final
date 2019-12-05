package com.banking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
/*
 * @author  AKUTHOTA.RAGHU 
 * @version 1.0
 * @since   2019-12-05
 * Here, Constants - UserTransactionRequestDto for output response
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTransactionResponseDto {

	private String message;
	private Integer statusCode;

	private List<UserTransactionRequestDto> transactionDetails;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public List<UserTransactionRequestDto> getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(List<UserTransactionRequestDto> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}
