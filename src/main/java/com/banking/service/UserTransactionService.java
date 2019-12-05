package com.banking.service;

import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.FundTransferResponseDto;

import javassist.NotFoundException;

@FunctionalInterface
public interface UserTransactionService {

	public FundTransferResponseDto fundTransfer(FundTransferRequestDto fundTransferRequestDto) throws NotFoundException;
}
