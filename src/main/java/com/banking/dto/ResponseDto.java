package com.banking.dto;

/**
 * FundTransferResponseDto - In this response dto's, we can set the response
 * details of the api. either success/failure values
 * 
 * @author Govindasamy.C
 * @version V1.1
 * @created date - 04-12-2019
 */
public class ResponseDto {

	private String status;
	private Integer statusCode;
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
