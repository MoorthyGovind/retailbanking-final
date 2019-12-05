package com.banking.util;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.entity.UserTransaction;

/**
 * ConverterUtil - ConverterUtil mainly used for converting the objects to
 * another object
 * 
 * @author Govindasamy.C Created Date - 05-12-2019
 */
public class ConverterUtil {

	private ConverterUtil() {
	}

	/**
	 * convert the FundTransferRequestDto values to UserTransaction entity.
	 * 
	 * @param fundTransferRequestDto
	 * @return UserTransaction object
	 */
	public static UserTransaction convertDtoToTransactionEntity(FundTransferRequestDto fundTransferRequestDto) {
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setTransactionDate(LocalDate.now());
		userTransaction.setTransactionAmount(fundTransferRequestDto.getTransferAmount());
		userTransaction.setTransactionType(AppConstant.ACCOUNT_TYPE);
		userTransaction.setCurrentBalanceAmount(0.00);
		userTransaction.setRemarks(fundTransferRequestDto.getRemarks());
		return userTransaction;
	}
}
