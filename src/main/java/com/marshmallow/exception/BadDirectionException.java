package com.marshmallow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * An exception which should be thrown when attempting to navigate the cleaner improperly.
 * Current usages include when providing an unrecognized cardinal direction, and when navigating out of bounds.
 * @author skircher
 */

// Use @ResponseStatus to clarify this is a bad request / 400 not a 500
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadDirectionException extends RuntimeException {
    public BadDirectionException(String message) {
            super(message);
        }
}

