package com.crud.myFinalCrud.exceptions;

public enum ErrorMesageEnum {

    CLIENT_NOT_FOUND("Client not found"),
    ORDER_NOT_FOUND("Order not found"),
    CLIENT_NAME_NOT_FOUND("Client name not found"),
    INVALID_REQUEST("Invalid request");


    private final String message;

    ErrorMesageEnum(String message) {
        this.message = message;
    }

    public String getMessage() { // вот этим методом.
        return message;
    }
}