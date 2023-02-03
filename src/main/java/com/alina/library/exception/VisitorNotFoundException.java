package com.alina.library.exception;

public class VisitorNotFoundException extends RuntimeException {
    public VisitorNotFoundException(Long id) {
        super("The visitor id " + id + "doesn't exist in our records");
    }
}
