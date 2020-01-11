package com.bae.manager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.InvalidEntryException;
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
	
	private String length81 = "AACATCAAGAGGCTCGGTAGCTGCGTCGGAGCTAAGGTGTATCTGGCATGTTCCCATCCTAGGTGGCCTTCTCAGGAGTAk";
	
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
	public void createAuthorTest() {
		assertThat(this.testAuthorWithId2).isEqualTo(this.service.createAuthor(this.testAuthor2));
	}
	
	@Test(expected = InvalidEntryException.class)
	public void createAuthorPenNameTooLongTest() {
		this.testAuthor2.setPenName(this.length81);
		this.service.createAuthor(testAuthor2);
	}
	
	@Test(expected = DuplicateValueException.class)
	public void createAuthorDuplicateAuthorTest() {
		this.service.createAuthor(testAuthor);
	}
	
	@Test
	public void findRepeatedAuthorExistsTest() {
		assertTrue(this.service.findRepeatedAuthor(this.testAuthor));
	}
	
	@Test
	public void findRepeatedAuthorDoesNotExistTest() {
		assertFalse(this.service.findRepeatedAuthor(this.testAuthor2));
	}
	
	@Test
	public void getAuthorsTest() {
		this.testAuthorWithId2 = this.service.createAuthor(this.testAuthor2);
		assertThat(Arrays.asList(new Author[] {this.testAuthorWithId, this.testAuthorWithId2})).isEqualTo(this.service.getAllAuthors());
	}
	
	@Test
	public void findAuthorByIdTest() {
		assertThat(this.testAuthorWithId).isEqualTo(this.service.findAuthorById(this.testAuthorWithId.getId()));
	}
	
	@Test
	public void deleteAuthorTest() {
		assertFalse(this.service.deleteAuthor(this.testAuthorWithId.getId()));
	}
	
	@Test
	public void removeOrphanedAuthorsTest() {
		assertThat(this.service.removeOrphanedAuthors()).isEmpty();
	}
}
