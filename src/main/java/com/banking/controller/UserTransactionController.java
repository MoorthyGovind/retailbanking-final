package com.banking.controller;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.ResponseDto;
import com.banking.dto.UserTransactionResponseDto;
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
@RequestMapping("/transactions")
public class UserTransactionController {
	private static final Logger logger = LoggerFactory.getLogger(UserTransactionController.class);

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
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping
	public ResponseEntity<ResponseDto> fundTransfer(
			@Valid @RequestBody FundTransferRequestDto fundTransferRequestDto) throws NotFoundException {
		logger.info("fund transaction ");
		ResponseDto fundTransferResponseDto = userTransactionService.fundTransfer(fundTransferRequestDto);
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

	/*
	 * This method is used for to get recent 5 transactions input parameter :
	 * Integer userAccountId return : UserTransactionResponseDto throws :
	 * NoResultException
	 */

	@CrossOrigin(origins = "*", allowedHeaders = "*")

	@GetMapping("/{userAccountId}")
	public UserTransactionResponseDto getRecentFiveTransactions(@PathVariable Integer userAccountId)
			throws NoResultException {

		UserTransactionResponseDto userTransactionResponse = userTransactionService
				.findRecentFiveTransactions(userAccountId);

		if (userTransactionResponse == null) {
			logger.info(" UserTransactionController - getRecentFiveTransactions - No Record Found ");
			throw new NoResultException(AppConstant.NO_RECORD_FOUND);
		}

		return userTransactionResponse;
	}

}
