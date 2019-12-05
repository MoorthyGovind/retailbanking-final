package com.banking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banking.constant.AppConstant;
import com.banking.dto.RegisterRequestDto;
import com.banking.dto.RegisterResponseDto;
import com.banking.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	RegisterRequestDto requestDto = new RegisterRequestDto();
	RegisterResponseDto responseDto = new RegisterResponseDto();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		requestDto.setPanNumber("PG672H6726");
		requestDto.setAddress1("Cbe");
		requestDto.setAddress2("Gandhipuram");
		requestDto.setDob("1992-04-23");
		requestDto.setFirstName("Moorthy");
		requestDto.setLastName("Govindasamy");
		requestDto.setPhone("8675958381");
		requestDto.setPinCode(641666);
	}

	@Test
	public void testRegisterUser() {
		responseDto.setStatus(AppConstant.SUCCESS);
		responseDto.setMessage(AppConstant.REGISTER_SUCCESS_MESSAGE);

		when(userService.registerUser(requestDto)).thenReturn(responseDto);

		ResponseEntity<RegisterResponseDto> response = userController.registerUser(requestDto);
		assertEquals(AppConstant.SUCCESS, response.getBody().getStatus());
		assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
		assertEquals(AppConstant.REGISTER_SUCCESS_MESSAGE, response.getBody().getMessage());
	}

	@Test
	public void testRegisterUserForFailure() {
		responseDto.setStatus(AppConstant.FAILURE);
		when(userService.registerUser(requestDto)).thenReturn(responseDto);

		ResponseEntity<RegisterResponseDto> response = userController.registerUser(requestDto);
		assertEquals(AppConstant.FAILURE, response.getBody().getStatus());
	}

}
