package com.marshmallow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom exception for any specifically handled malformed request elements not caught by @Valid tags.
 * Currently used to capture an invalid array of coordinates for oilPatches
 * @author skircher
 */

// Use @ResponseStatus to clarify this is a bad request / 400 not a 500
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MalformedRequestException extends RuntimeException {
    public MalformedRequestException(String message) {
        super(message);
    }
}
