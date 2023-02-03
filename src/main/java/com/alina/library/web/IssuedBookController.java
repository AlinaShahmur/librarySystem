package com.alina.library.web;

import com.alina.library.entity.IssuedBook;
import com.alina.library.service.IssuedBookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/issuedbook")
public class IssuedBookController {

    IssuedBookService issuedBookService;
    @GetMapping("/byVisitorId/{visitorId}")
    public ResponseEntity<List<IssuedBook>> getIssuedBooks(@PathVariable Long visitorId) {
        return new ResponseEntity<>(issuedBookService.getVisitorRecords(visitorId), HttpStatus.OK);
    }

    @GetMapping("/byBookId/{bookId}")
    public ResponseEntity<List<IssuedBook>> getBookIssuesHistory(@PathVariable Long bookId) {
        return new ResponseEntity<>(issuedBookService.getBookRecords(bookId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IssuedBook> issueBook(@Valid @RequestBody IssuedBook issuedBook) {
        return new ResponseEntity<>(issuedBookService.issueBook(issuedBook), HttpStatus.CREATED);
    }

    @PutMapping("/{issuedBookRecordId}")
    public ResponseEntity<IssuedBook> returnBook(@PathVariable Long issuedBookRecordId) {
        issuedBookService.returnBook(issuedBookRecordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
