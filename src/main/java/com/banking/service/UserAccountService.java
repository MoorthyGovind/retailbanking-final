package com.banking.service;

import java.util.List;

import com.banking.dto.ViewPayeeDto;

public interface UserAccountService {

	public List<ViewPayeeDto> getAllPayees(Integer accountId);

}
