package com.banking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.ResponseDto;
import com.banking.entity.UserAccount;
import com.banking.entity.UserTransaction;
import com.banking.repository.UserAccountRepository;
import com.banking.repository.UserTransactionRepository;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserTransactionServiceImplTest {

	@InjectMocks
	UserTransactionServiceImpl userTransactionServiceImpl;

	@Mock
	UserAccountRepository userAccountRepository;

	@Mock
	UserTransactionRepository userTransactionRepository;

	FundTransferRequestDto fundTransferRequestDto = new FundTransferRequestDto();
	ResponseDto fundTransferResponseDto = new ResponseDto();
	UserAccount userAccount = new UserAccount();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		fundTransferRequestDto.setAccountId(1);
		fundTransferRequestDto.setPayeeAccountId(2);
		fundTransferRequestDto.setTransferAmount(2000.00);
		fundTransferRequestDto.setRemarks("For Hospital Expenses");

		userAccount.setId(1);
		userAccount.setAccountNumber(607383878844L);
		userAccount.setAccountType("Savings");
		userAccount.setBalanceAmount(10000.00);
		userAccount.setMinimumBalance(1000.00);
		userAccount.setCreatedDate(LocalDateTime.now());
	}

	@Test
	public void testFundTransfer() throws NotFoundException {
		Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);

		UserAccount payeeAccount = new UserAccount();
		payeeAccount.setId(2);
		payeeAccount.setBalanceAmount(3000.00);
		Optional<UserAccount> optionalPayeeAccount = Optional.of(payeeAccount);

		when(userAccountRepository.findById(fundTransferRequestDto.getAccountId())).thenReturn(optionalUserAccount);
		when(userAccountRepository.findById(fundTransferRequestDto.getPayeeAccountId()))
				.thenReturn(optionalPayeeAccount);

		ResponseDto response = userTransactionServiceImpl.fundTransfer(fundTransferRequestDto);
		assertEquals("SUCCESS", response.getStatus());
	}

	@Test
	public void testFundTransferForMinimumBalance() throws NotFoundException {
		userAccount.setBalanceAmount(500.00);
		Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);

		UserAccount payeeAccount = new UserAccount();
		payeeAccount.setId(2);
		payeeAccount.setBalanceAmount(3000.00);
		Optional<UserAccount> optionalPayeeAccount = Optional.of(payeeAccount);

		when(userAccountRepository.findById(fundTransferRequestDto.getAccountId())).thenReturn(optionalUserAccount);
		when(userAccountRepository.findById(fundTransferRequestDto.getPayeeAccountId()))
				.thenReturn(optionalPayeeAccount);

		ResponseDto response = userTransactionServiceImpl.fundTransfer(fundTransferRequestDto);
		assertEquals(AppConstant.FAILURE, response.getStatus());
		assertEquals(AppConstant.FUND_TRANSFER_MIN_BAL, response.getMessage());

	}

	@Test(expected = NotFoundException.class)
	public void testFundTransferForNoAccountsFound() throws NotFoundException {
		userAccount.setBalanceAmount(500.00);
		Optional<UserAccount> optionalUserAccount = Optional.of(userAccount);

		when(userAccountRepository.findById(fundTransferRequestDto.getAccountId())).thenReturn(optionalUserAccount);
		when(userAccountRepository.findById(fundTransferRequestDto.getPayeeAccountId()))
				.thenReturn(Optional.ofNullable(null));

		userTransactionServiceImpl.fundTransfer(fundTransferRequestDto);
	}

	@Test
	public void testGetTransactionNumber() {
		when(userTransactionRepository.findByTransactionId("T-883833")).thenReturn(new UserTransaction());
		String transactionNumber = userTransactionServiceImpl.getTransactionNumber();
		assertThat(transactionNumber).isNotNull();

	}
}
