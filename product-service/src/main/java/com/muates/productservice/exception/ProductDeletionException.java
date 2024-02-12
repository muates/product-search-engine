package com.muates.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductDeletionException extends RuntimeException {

    public ProductDeletionException() {
        super();
    }

    public ProductDeletionException(String message) {
        super(message);
    }

    public ProductDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
