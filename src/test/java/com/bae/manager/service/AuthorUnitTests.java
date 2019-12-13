package com.bae.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.repo.AuthorRepo;

@RunWith(SpringRunner.class)
public class AuthorUnitTests {
	
	@InjectMocks
	private AuthorService service;
	
	@Mock
	private AuthorRepo repo;
	
	private List<Author> authorList;
	
	private Author testAuthor;
	
	private Author testAuthorWIthId;
	
	final long id = 1L;
	
	@Before
	public void init() {
		this.authorList = new ArrayList<>();
		this.authorList.add(testAuthor);
		this.testAuthor = new Author("Terry", "Pratchett");
		this.testAuthorWIthId = new Author(testAuthor.getFirstName(), testAuthor.getLastName());
		this.testAuthorWIthId.setId(id);
		
	}
	
	@Test
	public void createAuthorTest() {
		when(this.repo.save(testAuthor)).thenReturn(testAuthorWIthId);
		assertEquals(this.testAuthorWIthId, this.service.createAuthor(testAuthor));
		verify(this.repo, times(1)).save(this.testAuthor);
	}

}
