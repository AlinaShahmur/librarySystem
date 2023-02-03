package com.alina.library.ControllersTests;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.alina.library.entity.Visitor;
import com.alina.library.repository.VisitorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitorControllerIntegrationTest {
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private VisitorRepository visitorRepository;

    @Autowired
    private ObjectMapper objectMapper;

	private Visitor[] visitors = new Visitor[] {
		new Visitor("Alina", "Shahmurov", LocalDate.of(1995, 8, 26)),
		new Visitor("Dolly", "Shahmurov", LocalDate.of(1991, 3, 1)),
	};


	@BeforeEach
	void setup() {
		for (Visitor visitor : visitors) {
			visitorRepository.save(visitor);
		}
	}

    @AfterEach
    void cleanup() {
        visitorRepository.deleteAllWithResetIds();  
    }

    @Test
    public void getVisitorsTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/visitor/all");

		mockMvc.perform(request)
		.andExpectAll(
			status().is2xxSuccessful(),
			content().contentType(org.springframework.http.MediaType.APPLICATION_JSON),
			jsonPath("$.size()").value(visitors.length),
            jsonPath("$.[?(@.id == \"1\" && @.firstName == \"Alina\" && @.lastName == \"Shahmurov\" && @.birthDate == \"1995-08-26\")]").exists()
		);
    }

    @Test
    public void getVisitorTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/visitor/1");

		mockMvc.perform(request)
		.andExpectAll(
			status().is2xxSuccessful(),
			content().contentType(org.springframework.http.MediaType.APPLICATION_JSON),
			jsonPath("$.firstName").value(visitors[0].getFirstName()),
            jsonPath("$.lastName").value(visitors[0].getLastName())
		);
    }

    @Test
    public void addValidVisitorTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/visitor")
        .content(objectMapper.writeValueAsString(new Visitor("Chuck", "Bardak", LocalDate.of(2013, 10, 30))))
        .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(request)
        .andExpect(status().isCreated());
    }

    @Test
    public void addInvalidVisitorTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/visitor")
        .content(objectMapper.writeValueAsString(new Visitor("", "", LocalDate.of(2013, 10, 30))))
        .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(request)
        .andExpect(status().is4xxClientError());
    }
}
