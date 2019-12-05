package com.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.ViewPayeeDto;
import com.banking.entity.User;
import com.banking.entity.UserAccount;
import com.banking.repository.UserAccountRepository;
import com.banking.repository.UserRepository;
import com.banking.util.ConverterUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ViewPayeeDto> getAllPayees(Integer accountId) {
		List<ViewPayeeDto> viewPayeeDtos = new ArrayList<>();
		List<UserAccount> userAccounts = userAccountRepository.findAllByIdNot(accountId);
		userAccounts.forEach(userAccount -> {
			Optional<User> user = userRepository.findById(userAccount.getUserId());
			if (user.isPresent()) {
				viewPayeeDtos.add(ConverterUtil.convertTransactionToPayeeDto(userAccount, user.get()));
			}
		});
		return viewPayeeDtos;
	}
}
