package com.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.ResponseDto;
import com.banking.dto.UserTransactionRequestDto;
import com.banking.dto.UserTransactionResponseDto;
import com.banking.entity.User;
import com.banking.entity.UserAccount;
import com.banking.entity.UserTransaction;
import com.banking.repository.UserAccountRepository;
import com.banking.repository.UserRepository;
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

	@Autowired
	UserRepository userRepository;

	/**
	 * @throws NotFoundException
	 * 
	 */
	@Override
	public ResponseDto fundTransfer(FundTransferRequestDto fundTransferRequestDto) throws NotFoundException {
		ResponseDto fundTransferResponseDto = new ResponseDto();
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

				// Get Transaction Number
				String transactionNumber = getTransactionNumber();

				userTransaction.setTransactionId(transactionNumber);
				userTransaction.setUserAccountId(userAccount);
				userTransaction.setPayeeAccountId(userPayeeAccount);
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

	/*
	 * This method is used for to get recent 5 transactions input parameter :
	 * Integer userAccountId return : UserTransactionResponseDto throws :
	 * NoResultException
	 */
	@Override
	public UserTransactionResponseDto findRecentFiveTransactions(Integer userAccountId) throws NoResultException {

		UserTransactionResponseDto userTransactionResponseDto = new UserTransactionResponseDto();
		List<UserTransactionRequestDto> response = new ArrayList<>();

		List<UserTransaction> userTransactionResponse = userTransactionRepository
				.findTop5ByUserAccountIdIdOrderByTransactionDateDesc(userAccountId);

		if (null != userTransactionResponse && userTransactionResponse.size() > AppConstant.ZERO) {

			response = userTransactionResponse.stream().map(temp -> {

				UserTransactionRequestDto obj = new UserTransactionRequestDto();
				Optional<User> user = userRepository.findById(temp.getPayeeAccountId().getUserId());
				if (user.isPresent()) {
					obj.setPayeeName(user.get().getFirstName() + " " + user.get().getLastName());
				}
				obj.setPayeeAccountNumber(temp.getPayeeAccountId().getAccountNumber());
				obj.setTransactionType(temp.getTransactionType());
				obj.setTransactionDate(temp.getTransactionDate());
				obj.setTransactionAmount(temp.getTransactionAmount());
				obj.setCurrentBalance(temp.getCurrentBalanceAmount());

				return obj;
			}).collect(Collectors.toList());

			userTransactionResponseDto.setMessage(AppConstant.OPERATION_SUCCESS);
			userTransactionResponseDto.setStatusCode(200);
			userTransactionResponseDto.setTransactionDetails(response);

			return userTransactionResponseDto;

		} else {
			userTransactionResponseDto.setMessage(AppConstant.NO_RECORD_FOUND);
			userTransactionResponseDto.setStatusCode(404);
			userTransactionResponseDto.setTransactionDetails(response);
		}
		return userTransactionResponseDto;
	}

	/**
	 * get the transaction number
	 * 
	 * @return
	 */
	public String getTransactionNumber() {
		Integer transactionId = CommonUtil.getTransactionNumber();
		String transactionNumber = AppConstant.GET_TRANSACTION_NO_PREFIX + transactionId;

		UserTransaction userTransaction = userTransactionRepository.findByTransactionId(transactionNumber);
		Optional<UserTransaction> isUserTransaction = Optional.ofNullable(userTransaction);
		if (isUserTransaction.isPresent()) {
			getTransactionNumber();
		}
		return transactionNumber;

	}
}
