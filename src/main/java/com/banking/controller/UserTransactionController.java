package com.banking.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.FundTransferResponseDto;
import com.banking.service.UserTransactionService;

import javassist.NotFoundException;

/**
 * UserTransaction Controller - we can implement the banking operations like
 * fund transfer.
 * 
 * @author Govindasamy.C
 * @version V1.1
 * @created date - 04-12-2019
 */
@RestController
@RequestMapping("/user/transactions")
public class UserTransactionController {

	@Autowired
	UserTransactionService userTransactionService;

	/**
	 * In this method, we are implementing the fund transfer operations.
	 * 
	 * @param fundTransferRequestDto -> getting input params -> accountId,
	 *                               payeeAccountId, transferAmount & remarks.
	 * @return
	 * @throws NotFoundException
	 */
	@PostMapping
	public ResponseEntity<FundTransferResponseDto> fundTransfer(
			@Valid @RequestBody FundTransferRequestDto fundTransferRequestDto) throws NotFoundException {
		FundTransferResponseDto fundTransferResponseDto = userTransactionService.fundTransfer(fundTransferRequestDto);
		// Check the response status is success or not.
		Optional<String> isSuccess = Optional.ofNullable(fundTransferResponseDto.getStatus());
		if (isSuccess.isPresent()) {
			if (fundTransferResponseDto.getStatus().equals(AppConstant.SUCCESS)) {
				fundTransferResponseDto.setStatusCode(HttpStatus.OK.value());
			} else {
				fundTransferResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
			}
		} else {
			fundTransferResponseDto.setStatus(AppConstant.FAILURE);
			fundTransferResponseDto.setMessage(AppConstant.FUND_TRANSFER_ERROR);
			fundTransferResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		return new ResponseEntity<>(fundTransferResponseDto, HttpStatus.OK);
	}
}
