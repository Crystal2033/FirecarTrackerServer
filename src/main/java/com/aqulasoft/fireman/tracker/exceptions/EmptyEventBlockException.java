package com.aqulasoft.fireman.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event block is empty.")
public class EmptyEventBlockException extends Exception {
}
