package com.bae.manager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.EntryNotFoundException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.AuthorRepo;

@RunWith(MockitoJUnitRunner.class)
public class AuthorUnitTests {

	@InjectMocks
	private AuthorService service;

	@Mock
	private AuthorRepo repo;

	private List<Author> authorList;

	private Author testAuthor;

	private Author testAuthorWithId;
	
	private Author testAuthor2;
	
	private Author testAuthor2WithId;

	private Author testAuthorFail;

	private Author testAuthorFailWithId;

	private final long invalidId = 3L;
	
	private final long id2 = 2L;

	private final long id = 1L;
	
	private String length81 = "AACATCAAGAGGCTCGGTAGCTGCGTCGGAGCTAAGGTGTATCTGGCATGTTCCCATCCTAGGTGGCCTTCTCAGGAGTAk";

	@Before
	public void init() {
		this.authorList = new ArrayList<>();
		
		this.testAuthor = new Author("Terry Pratchett");
		this.testAuthorWithId = new Author(testAuthor.getPenName());
		this.testAuthorWithId.setId(this.id);
		
		this.testAuthor2 = new Author("Neil Gaiman");
		this.testAuthor2WithId = new Author(testAuthor2.getPenName());
		this.testAuthor2WithId.setId(this.id2);
		
		this.authorList.add(testAuthor);
		this.authorList.add(testAuthor2);
		
		this.testAuthorFail = new Author(this.length81);
		this.testAuthorFail.setId(id);
		this.testAuthorFailWithId = new Author(testAuthorFail.getPenName());
		this.testAuthorFailWithId.setId(id);

	}

	@Test
	public void createAuthorTest() {
		when(this.repo.save(this.testAuthor)).thenReturn(this.testAuthorWithId);
		assertEquals(this.testAuthorWithId, this.service.createAuthor(this.testAuthor));
		verify(this.repo, times(1)).save(this.testAuthor);
	}

	@Test
	public void createAuthorDuplicateEntryTest() {

		when(this.repo.findAll()).thenReturn(this.authorList);
		assertThrows(DuplicateValueException.class, () -> {
			this.service.createAuthor(this.testAuthor);
			verify(this.repo, times(1)).findAll();
		});
	}

	@Test
	public void createAuthorNameTooLargeTest() {
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createAuthor(this.testAuthorFail);
		});
	}

	@Test
	public void findDuplicateAuthorTest() {
		when(this.repo.findAll()).thenReturn(this.authorList);
		assertTrue(this.service.findRepeatedAuthor(this.testAuthor));
		assertFalse(this.service.findRepeatedAuthor(this.testAuthorFail));
		verify(this.repo, times(2)).findAll();
	}

	@Test
	public void getAllAuthorsTest() {
		when(this.repo.findAll()).thenReturn(this.authorList);
		assertEquals(this.authorList, this.service.getAllAuthors());
		verify(this.repo, times(1)).findAll();
	}

	@Test
	public void findAuthorByIdTest() {
		when(this.repo.findById(id)).thenReturn(Optional.of(this.authorList.get(0)));
		assertEquals(testAuthor, this.service.findAuthorById(id));
		verify(this.repo, times(1)).findById(id);
	}

	@Test
	public void findAuthorByIdMissingIdTest() {
		when(this.repo.findById(invalidId)).thenThrow(new EntryNotFoundException());
		assertThrows(EntryNotFoundException.class, () -> {
			this.service.findAuthorById(invalidId);
		});
	}
	
	@Test
	public void deleteAuthorTest() {
		when(this.repo.existsById(this.id)).thenReturn(true, false);
		
		assertFalse(this.service.deleteAuthor(this.id));
		assertThrows(EntryNotFoundException.class, () -> {
			this.service.deleteAuthor(this.id);
		});
		verify(this.repo, times(1)).deleteById(this.id);
		verify(this.repo, times(3)).existsById(this.id);	}
	
	@Test
	public void removeOrphanedAuthorsTest() {
		List<Author> NonOrphanedAuthors = new ArrayList<>();
		List<Author> authorsWithId = new ArrayList<>();
		authorsWithId.add(testAuthorWithId);
		authorsWithId.add(testAuthor2WithId);
		NonOrphanedAuthors.add(this.testAuthorWithId);
		Book testBook = new Book("The Colour of Magic", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testAuthor2WithId.setBooks(Stream.of(testBook).collect(Collectors.toSet()));
		when(this.repo.findAll()).thenReturn(authorsWithId, NonOrphanedAuthors);
		when(this.repo.existsById(this.id)).thenReturn(true, false);
		
		assertEquals(NonOrphanedAuthors, this.service.removeOrphanedAuthors());
		
		verify(this.repo, times(2)).findAll();
		verify(this.repo, times(1)).deleteById(this.id);
		
	}

}
