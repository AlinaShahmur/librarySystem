package com.alina.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("The book id " + id + "doesn't exist in our records");
    }
}
