package com.blackcode.book_store_be.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
