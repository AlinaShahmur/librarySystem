package com.alina.library.ControllersTests;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alina.library.entity.Book;
import com.alina.library.entity.IssuedBook;
import com.alina.library.entity.Visitor;
import com.alina.library.repository.BookRepository;
import com.alina.library.repository.IssuedBookRepository;
import com.alina.library.repository.VisitorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class IssuedBookControllerIntegrationTest {
    
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private VisitorRepository visitorRepository;

    @Autowired
	private BookRepository bookRepository;

    @Autowired
	private IssuedBookRepository issuedBookRepository;
    @Autowired
    private ObjectMapper objectMapper;


    private Book[] books = new Book[] {
		new Book("Harry Potter", "Johan Rowling")
	};

    private Visitor[] visitors = new Visitor[] {
		new Visitor("Alina", "Shahmurov", LocalDate.of(1995, 8, 26))
	};


	@BeforeEach
	void setup() {
		for (Book book : books) {
			bookRepository.save(book);
		}
        for (Visitor visitor : visitors) {
			visitorRepository.save(visitor);
		}
        IssuedBook issuedBook = new IssuedBook(LocalDate.of(2023,2,1), LocalDate.of(2023,3,1));
        issuedBook.setBook(books[0]);
        issuedBook.setVisitor(visitors[0]);
        issuedBook.setClosed(false);
        issuedBookRepository.save(issuedBook);
    }

    @AfterEach
    void cleanup() {
        issuedBookRepository.deleteAllWithResetIds();
        bookRepository.deleteAllWithResetIds();  
        visitorRepository.deleteAllWithResetIds();  
    }

    @Test
    public void getIssuedBooksTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/issuedbook/byVisitorId/1");

		mockMvc.perform(request)
		.andExpectAll(
			status().is2xxSuccessful(),
			content().contentType(org.springframework.http.MediaType.APPLICATION_JSON),
            jsonPath("$.size()").value(1),
            jsonPath("$.[?(@.book.id == \"1\" && @.visitor.id == \"1\" && @.dueDate == \"2023-03-01\" && @.issuDate == \"2023-02-01\")]").exists()
		);
    }

    @Test
    public void getBooksHistoryTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/issuedbook/byBookId/1");

		mockMvc.perform(request)
		.andExpectAll(
			status().is2xxSuccessful(),
			content().contentType(org.springframework.http.MediaType.APPLICATION_JSON),
            jsonPath("$.size()").value(1),
            jsonPath("$.[?(@.book.id == \"1\" && @.visitor.id == \"1\" && @.dueDate == \"2023-03-01\" && @.issuDate == \"2023-02-01\")]").exists()
		);
    }


    @Test
    public void addValidIssuedBooksTest() throws Exception {
        IssuedBook issuedBook = new IssuedBook(LocalDate.of(2023,2,1), LocalDate.of(2023,3,1));
        issuedBook.setBook(books[0]);
        issuedBook.setVisitor(visitors[0]);

        RequestBuilder request = MockMvcRequestBuilders.post("/issuedbook")
        .content(objectMapper.writeValueAsString(issuedBook))
        .contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
		.andExpectAll(
			status().isCreated()
		);
    }

    @Test
    public void addInvalidIssuedBooksTest() throws Exception {
        IssuedBook issuedBook = new IssuedBook(LocalDate.of(2023,2,1), LocalDate.of(2022,3,1));
        issuedBook.setBook(books[0]);
        issuedBook.setVisitor(visitors[0]);

        RequestBuilder request = MockMvcRequestBuilders.post("/issuedbook")
        .content(objectMapper.writeValueAsString(issuedBook))
        .contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
		.andExpectAll(
			status().is4xxClientError()
		);
    }

}
