package com.alina.library.service;

import org.springframework.stereotype.Service;

import com.alina.library.entity.Book;
import com.alina.library.exception.BookNotFoundException;
import com.alina.library.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BookService {

    BookRepository bookRepository;
    public Book getBook(Long id) {
        return unwrapBook(bookRepository.findById(id), id);
    }

    public List<Book> getBooks() {
        return (List<Book>)bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        book.setAvaliable(true);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book unwrapBook(Optional<Book> book, Long id) {
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException(id);
        }
    }

}
