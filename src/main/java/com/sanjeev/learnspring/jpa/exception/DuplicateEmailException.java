package com.sanjeev.learnspring.jpa.exception;
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Customer with email already exists: " + email);
    }
}
