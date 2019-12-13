package com.bae.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate Field Entry")

public class DuplicateValueException extends RuntimeException {

	private static final long serialVersionUID = -1256779688860136289L;
}
