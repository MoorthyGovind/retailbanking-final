package com.banking.service;

import com.banking.dto.LoginDto;
import com.banking.dto.LoginResponseDto;

public interface LoginService {
	
	public LoginResponseDto login(LoginDto userLoginDto);

}
