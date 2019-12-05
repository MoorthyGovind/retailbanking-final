package com.banking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banking.constant.AppConstant;
import com.banking.dto.FundTransferRequestDto;
import com.banking.dto.ResponseDto;
import com.banking.service.UserTransactionService;

import javassist.NotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserTransactionControllerTest {

	@InjectMocks
	UserTransactionController userTransactionController;

	@Mock
	UserTransactionService userTransactionService;

	FundTransferRequestDto fundTransferRequestDto = new FundTransferRequestDto();
	ResponseDto fundTransferResponseDto = new ResponseDto();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		fundTransferRequestDto.setAccountId(1);
		fundTransferRequestDto.setPayeeAccountId(2);
		fundTransferRequestDto.setTransferAmount(2000.00);
		fundTransferRequestDto.setRemarks("For Hospital Expenses");
	}

	@Test
	public void testFundTransfer() throws NotFoundException {
		fundTransferResponseDto.setStatus(AppConstant.SUCCESS);
		fundTransferResponseDto.setMessage(AppConstant.FUND_TRANSFER_SUCCESS);
		fundTransferResponseDto.setStatusCode(200);

		when(userTransactionService.fundTransfer(fundTransferRequestDto)).thenReturn(fundTransferResponseDto);

		ResponseEntity<ResponseDto> response = userTransactionController
				.fundTransfer(fundTransferRequestDto);
		assertEquals("SUCCESS", response.getBody().getStatus());
		assertEquals(200, response.getBody().getStatusCode());
	}

	@Test
	public void testFundTransferForFailure() throws NotFoundException {
		fundTransferResponseDto.setStatus(AppConstant.FAILURE);
		fundTransferResponseDto.setMessage(AppConstant.FUND_TRANSFER_ERROR);
		fundTransferResponseDto.setStatusCode(400);

		when(userTransactionService.fundTransfer(fundTransferRequestDto)).thenReturn(fundTransferResponseDto);

		ResponseEntity<ResponseDto> response = userTransactionController
				.fundTransfer(fundTransferRequestDto);
		assertEquals("FAILURE", response.getBody().getStatus());
		assertEquals(400, response.getBody().getStatusCode());
	}

	@Test
	public void testFundTransferForBadRequest() throws NotFoundException {
		when(userTransactionService.fundTransfer(fundTransferRequestDto)).thenReturn(new ResponseDto());

		ResponseEntity<ResponseDto> response = userTransactionController
				.fundTransfer(fundTransferRequestDto);
		assertEquals("FAILURE", response.getBody().getStatus());
		assertEquals(400, response.getBody().getStatusCode());
	}
}
