package com.bae.manager.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.repo.AuthorRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private AuthorRepo repo;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private long id;
	
	private Author testAuthor;
	
	private Author testAuthorWithId;
	
	private Author testAuthor2;
	
	private Author testAuthor2WithId;
	
	@Before
	public void init() {
		this.repo.deleteAll();
		
		this.testAuthor = new Author("Terry Pratchett");
		this.testAuthorWithId = this.repo.save(this.testAuthor);
		this.id = this.testAuthorWithId.getId();

		this.testAuthor2 = new Author("Neil Gaiman");
		this.testAuthor2WithId = new Author(this.testAuthor2.getPenName());
		this.testAuthor2WithId.setId(this.id + 1);
		
		
	}
	
	@Test
	public void createAuthorTest() throws Exception {
		String result = this.mock
						.perform(request(HttpMethod.POST, "/author/createAuthor").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(this.testAuthor2)).accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		assertEquals(this.mapper.writeValueAsString(this.testAuthor2WithId), result);
	}
	
	@Test
	public void getAuthorsTest() throws Exception {
		List<Author> authorList = new ArrayList<>();
		authorList.add(this.testAuthorWithId);
		
		String content = this.mock.perform(request(HttpMethod.GET, "/author/getAll").accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(authorList), content);
	}

}
