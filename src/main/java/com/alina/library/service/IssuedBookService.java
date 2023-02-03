package com.alina.library.service;

import org.springframework.stereotype.Service;

import com.alina.library.entity.Book;
import com.alina.library.entity.IssuedBook;
import com.alina.library.repository.BookRepository;
import com.alina.library.repository.IssuedBookRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Service
public class IssuedBookService {
    IssuedBookRepository issuedBookRepository;
    BookRepository bookRepository;
    public IssuedBook getRecordById(Long id) {
        return issuedBookRepository.findById(id).get();
    }

    public List<IssuedBook> getVisitorRecords(Long visitorId) {
        return issuedBookRepository.findByVisitorId(visitorId);
    }

    public List<IssuedBook> getBookRecords(Long bookId) {
        return issuedBookRepository.findByBookId(bookId);
    }

    public IssuedBook issueBook(IssuedBook issuedBook) {
        return issuedBookRepository.save(issuedBook);
    }

    public void returnBook(Long id) {
        IssuedBook issuedBook = issuedBookRepository.findById(id).get();
        issuedBook.setClosed(true);
        Book book = bookRepository.findById(issuedBook.getBook().getId()).get();
        book.setAvaliable(true);        
        issuedBookRepository.save(issuedBook);
        bookRepository.save(book);
    }
}
