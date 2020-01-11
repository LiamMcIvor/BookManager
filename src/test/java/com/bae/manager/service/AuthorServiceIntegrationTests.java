package com.bae.manager.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.repo.AuthorRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceIntegrationTests {

	@Autowired
	private AuthorRepo repo;

	@Autowired
	private AuthorService service;
	
	private Author testAuthor;
	
	private Author testAuthorWithId;
	
	private Author testAuthor2;
	
	private Author testAuthorWithId2;
	
	private final Long id = 2L;
	
	@Before
	public void init() {
		this.repo.deleteAll();
		
		this.testAuthor = new Author("Terry Pratchett");
		this.testAuthorWithId = this.repo.save(this.testAuthor);
		this.testAuthor2 = new Author("Neil Gaiman");
		this.testAuthorWithId2 = new Author(this.testAuthor2.getPenName());
		this.testAuthorWithId2.setId(this.id);
	}
	
	@Test
	public void 
}
