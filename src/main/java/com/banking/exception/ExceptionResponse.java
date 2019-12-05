package com.banking.exception;

/*
 * @author  AKUTHOTA.RAGHU 
 * @version 1.0
 * @since   2019-12-05
 * Here, ExceptionResponse - for sending the error response and status code to end user
 */
public class ExceptionResponse {

	private String errorMessage;
	private String requestedURI;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void callerURL(final String requestedURI) {
		this.requestedURI = requestedURI;
	}

}
