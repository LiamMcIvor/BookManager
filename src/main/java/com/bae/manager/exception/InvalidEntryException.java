package com.bae.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid Field Entry")
public class InvalidEntryException extends RuntimeException {

	private static final long serialVersionUID = 7365904091460376364L;
}
