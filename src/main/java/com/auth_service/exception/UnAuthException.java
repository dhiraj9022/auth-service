package com.auth_service.exception;

public class UnAuthException extends RuntimeException{
    public UnAuthException(String message){
        super(message);
    }
}
