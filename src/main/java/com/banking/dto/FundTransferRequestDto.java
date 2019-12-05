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

	private Integer accountId;
	private Integer payeeAccountId;

	private Double transferAmount;
	private String remarks;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getPayeeAccountId() {
		return payeeAccountId;
	}

	public void setPayeeAccountId(Integer payeeAccountId) {
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
