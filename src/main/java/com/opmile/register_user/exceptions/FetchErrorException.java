package com.opmile.register_user.exceptions;

import java.io.IOException;

public class FetchErrorException extends RuntimeException {
    public FetchErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetchErrorException(String message) {
        super(message);
    }
}
