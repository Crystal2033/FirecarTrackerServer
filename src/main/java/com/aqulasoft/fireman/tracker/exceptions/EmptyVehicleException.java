package com.aqulasoft.fireman.tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Vehicle is empty.")
public class EmptyVehicleException extends Exception {

}
