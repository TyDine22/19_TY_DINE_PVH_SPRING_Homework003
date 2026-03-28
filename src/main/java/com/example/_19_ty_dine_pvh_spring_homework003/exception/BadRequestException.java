package com.example._19_ty_dine_pvh_spring_homework003.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
