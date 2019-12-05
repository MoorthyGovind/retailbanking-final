package com.banking.dto;

/**
 * FundTransferRequestDto - In this dto's we can get the values for fund
 * transfer operations.
 * 
 * @author Govindasamy.C
 * @version V1.1
 * @created date - 04-12-2019
 */
public class FundTransferRequestDto {
	
	private Long accountId;
	private Long payeeAccountId;
	
	private Double transferAmount;
	private String remarks;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getPayeeAccountId() {
		return payeeAccountId;
	}

	public void setPayeeAccountId(Long payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}

	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
