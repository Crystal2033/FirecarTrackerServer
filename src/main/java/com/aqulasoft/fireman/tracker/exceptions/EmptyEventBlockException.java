package com.aqulasoft.fireman.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Array positions is empty.")
public class EmptyEventBlockException extends Exception {
}
