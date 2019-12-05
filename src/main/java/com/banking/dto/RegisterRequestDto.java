package com.banking.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.banking.constant.AppConstant;

/**
 * * The RegisterRequestDto class is a Dto class that has 11 fields
 * 
 * @author Janani
 * @since 2019-12-5
 *
 */
public class RegisterRequestDto {

	@NotBlank(message = AppConstant.FIRST_NAME_ERROR_MESSAGE)
	private String firstName;

	private String lastName;

	/*
	 * @Pattern(regexp = "yyyy-MM-dd", message = AppConstant.DOB_INVALID)
	 */	private String dob;

	@NotBlank(message = AppConstant.EMAIL_ERROR_MESSAGE)
	@Email(message = AppConstant.EMAIL_ADDRESS_ERROR_MESSAGE)
	private String emailAddress;

	@NotBlank(message = AppConstant.MOBILENUMBER_ERROR_MESSAGE)
	@Pattern(regexp = "(^$|[0-9]{10})", message = AppConstant.MOBILE_INVALID_MESSAGE)
	private String phone;

	@NotBlank(message = AppConstant.ADDRESS_ERROR_MESSAGE)
	private String address1;

	private String address2;

	private Integer pinCode;

	private String panNumber;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
}
