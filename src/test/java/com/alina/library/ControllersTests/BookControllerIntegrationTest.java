package com.alina.library.ControllersTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alina.library.entity.Book;

import com.alina.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

	private Book[] books = new Book[] {
		new Book("Harry Potter", "Johan Rowling"),
		new Book("The Night in Lisbon", "Erich Maria Remarque"),
		new Book("Brody Castle", " Cronin Archibald"),
	};


	@BeforeEach
	void setup() {
		for (Book book : books) {
			bookRepository.save(book);
		}
	}

    @AfterEach
    void cleanup() {
        bookRepository.deleteAllWithResetIds();  
    }

	@Test
	public void getBookByIdTest() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get("/book/1");
        mockMvc.perform(request)
        .andExpectAll(
            status().is2xxSuccessful(),
            content().contentType(MediaType.APPLICATION_JSON),
            jsonPath("$.title").value(books[0].getTitle()),
            jsonPath("$.author").value(books[0].getAuthor())
        );
	}

    @Test
    public void validBookCreation() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/book")
        .content(objectMapper.writeValueAsString(new Book("The Count of Monte Cristo","Alexandre Duma")))
        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
        .andExpect(status().isCreated());
    }

    @Test
    public void invalidBookCreation() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/book")
        .content(objectMapper.writeValueAsString(new Book("","")));
        
        mockMvc.perform(request)
        .andExpect(status().is4xxClientError());
    }
}
