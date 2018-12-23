package com.vitrenko.resourceoptimizer.service;

import lombok.Getter;

import static java.lang.String.format;

@Getter
public class EmailAlreadyExistException extends RuntimeException {

    private final String email;

    public EmailAlreadyExistException(String email) {
        super(format("AppUser with email %s already exists", email));
        this.email = email;
    }
}
