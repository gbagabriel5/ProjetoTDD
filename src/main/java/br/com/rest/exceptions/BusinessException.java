package br.com.rest.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private String error;
    private String details;

    public BusinessException(String error, String details) {
        super(error);
        this.error = error;
        this.details = details;
    }
}
