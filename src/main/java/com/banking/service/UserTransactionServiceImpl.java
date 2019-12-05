package com.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.FundTransferResponseDto;
import com.banking.entity.UserAccount;
import com.banking.entity.UserTransaction;
import com.banking.repository.UserAccountRepository;
import com.banking.repository.UserTransactionRepository;
import com.banking.util.CommonUtil;
import com.banking.util.ConverterUtil;

import javassist.NotFoundException;

@Service
public class UserTransactionServiceImpl implements UserTransactionService {

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	UserTransactionRepository userTransactionRepository;

	/**
	 * @throws NotFoundException
	 * 
	 */
	@Override
	public FundTransferResponseDto fundTransfer(FundTransferRequestDto fundTransferRequestDto)
			throws NotFoundException {
		FundTransferResponseDto fundTransferResponseDto = new FundTransferResponseDto();
		Optional<UserAccount> userAccountDetail = userAccountRepository.findById(fundTransferRequestDto.getAccountId());
		Optional<UserAccount> userPayeeAccountDetail = userAccountRepository
				.findById(fundTransferRequestDto.getPayeeAccountId());
		if (userAccountDetail.isPresent() && userPayeeAccountDetail.isPresent()) {
			// User Account Details
			UserAccount userAccount = userAccountDetail.get();
			UserAccount userPayeeAccount = userPayeeAccountDetail.get();

			// Check Minimum balance from user accounts.
			Double totalAmount = fundTransferRequestDto.getTransferAmount() + userAccount.getMinimumBalance();
			if (totalAmount <= userAccount.getBalanceAmount()) {
				UserTransaction userTransaction = ConverterUtil.convertDtoToTransactionEntity(fundTransferRequestDto);
				Double debitAmount = userAccount.getBalanceAmount() - fundTransferRequestDto.getTransferAmount();
				userAccount.setBalanceAmount(debitAmount);
        
				//Get Transaction Number
				String transactionNumber = getTransactionNumber();
				
				userTransaction.setTransactionId(transactionNumber);
				userTransaction.setUserAccountId(userAccount);
				userTransactionRepository.save(userTransaction);

				// Credit the payee acoount balance amount
				Double creditAmount = userPayeeAccount.getBalanceAmount() + fundTransferRequestDto.getTransferAmount();
				userPayeeAccount.setBalanceAmount(creditAmount);
				userAccountRepository.save(userPayeeAccount);

				fundTransferResponseDto.setStatus(AppConstant.SUCCESS);
				fundTransferResponseDto.setMessage(AppConstant.FUND_TRANSFER_SUCCESS);
			} else {
				fundTransferResponseDto.setStatus(AppConstant.FAILURE);
				fundTransferResponseDto.setMessage(AppConstant.FUND_TRANSFER_MIN_BAL);
			}
		} else {
			throw new NotFoundException("No Accounts Found.");
		}

		return fundTransferResponseDto;
	}
	
	/**
	 * get the transaction number
	 * @return
	 */
	public String getTransactionNumber() {
		Integer transactionId = CommonUtil.getTransactionNumber();
		String transactionNumber = AppConstant.GET_TRANSACTION_NO_PREFIX + transactionId;
		
		UserTransaction userTransaction = userTransactionRepository.findByTransactionId(transactionNumber);
		Optional<UserTransaction> isUserTransaction = Optional.ofNullable(userTransaction);
		if(isUserTransaction.isPresent()) {
			getTransactionNumber();
		}
		return transactionNumber;
		
	}
}
