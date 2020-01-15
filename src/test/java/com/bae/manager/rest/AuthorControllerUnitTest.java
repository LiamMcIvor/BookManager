package com.bae.manager.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.service.AuthorService;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerUnitTest {
	
	@InjectMocks
	private AuthorController controller;
	
	@Mock
	private AuthorService service;
	
	private List<Author> authorList;
	
	private Author testAuthor;
	
	private Author testAuthorWithId;
	
	private final long id = 1L;
	
	@Before
	public void init() {
		this.authorList = new ArrayList<>();
		this.testAuthor = new Author("Terry Pratchett");
		this.authorList.add(testAuthor);
		this.testAuthorWithId = new Author(testAuthor.getPenName());
		this.testAuthorWithId.setId(this.id);
		
	}

	@Test
	public void createAuthorTest() {
		when(this.service.createAuthor(testAuthor)).thenReturn(testAuthorWithId);
		assertEquals(this.testAuthorWithId, this.controller.createAuthor(testAuthor));
		verify(this.service, times(1)).createAuthor(this.testAuthor);
	}
	
	@Test
	public void getAllAuthorsTest() {
		when(this.service.getAllAuthors()).thenReturn(this.authorList);
		assertFalse("Controller has found no authors", this.controller.getAllAuthors().isEmpty());
		verify(this.service, times(1)).getAllAuthors();
	}

}
