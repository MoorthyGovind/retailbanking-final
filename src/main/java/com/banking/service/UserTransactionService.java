package com.banking.service;

import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.FundTransferResponseDto;
import com.banking.dto.UserTransactionResponseDto;

import javassist.NotFoundException;

public interface UserTransactionService {

	public FundTransferResponseDto fundTransfer(FundTransferRequestDto fundTransferRequestDto) throws NotFoundException;

	public UserTransactionResponseDto findRecentFiveTransactions(Integer userAccountId);
}
