package com.bae.manager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.EntryNotFoundException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.AuthorRepo;
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceIntegrationTests {
	
	@Autowired
	private BookRepo repo;

	@Autowired
	private BookService service;
	
	@Autowired
	private AuthorRepo authorRepo;
	
	private Book testBook;
	
	private Book testBookWithid;
	
	private Book testBook2;
	
	private Book testBook2Withid;
	
	private final Long id = 2L;
	
	private final Long invalidId = 90L;
	
	private Author testAuthor;
	
	private Author testAuthorWithId;
	
	private Author testAuthor2;
	
	private Author testAuthorWithId2;	
	
	private final String length151 = "GAATACCGATCATGATCCCCAACGTGCATTTACCAGAGGGTCATATTCTAAATAGGGTATTATGAGTCATCTGAATCTGTCCATGCGATTCGGGGCACCGATGTCGCGGACACGGTTTAAAATCCTATCAACCAGGTGACAATATGCCATC";

	private final String length61 = "CCACGTTTGTACCTAACCAGCGATTAGTAGTGATCTGGTTATTTGGATAGCGCTTTTTGTT";
	
	@Before
	public void init() {
		this.repo.deleteAll();
		this.authorRepo.deleteAll();

		this.testAuthor = new Author("Terry Pratchett");
		this.testAuthorWithId = this.authorRepo.save(this.testAuthor);
		this.testAuthor2 = new Author("Neil Gaiman");
		this.testAuthorWithId2 = new Author(this.testAuthor2.getPenName());
		this.testAuthorWithId2.setId(this.id);
		
		this.testBook = new Book("The Colour of Magic", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBook2 = new Book("Good Omens", "N/A", 0, Owned.WISHLIST, Completion.TO_READ);
		this.testBook2Withid = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		this.testBook2Withid.setId(this.id);
		this.testBookWithid = this.repo.save(this.testBook);		
	}
	
	@Test
	public void createBookTest() {
		assertThat(this.testBook2Withid).isEqualTo(this.service.createBook(this.testBook2));
	}
	
	@Test
	public void deleteBookTest() {
		assertThat(this.service.deleteBook(this.testBookWithid.getId())).isFalse();
	}
	
	@Test(expected = EntryNotFoundException.class)
	public void deleteBookInvalidIdTest() {
		this.service.deleteBook(this.invalidId);
		fail();
	}
	
	@Test
	public void findBookByIdTest() {
		assertThat(this.testBookWithid).isEqualToComparingFieldByField(this.service.findBookById(this.testBookWithid.getId()));
	}
	
	@Test(expected = EntryNotFoundException.class)
	public void findBookInvalidIdTest() {
		this.service.findBookById(this.invalidId);
		fail();
	}
	
	@Test
	public void getBooksTest() {
		this.testBook2Withid = this.service.createBook(testBook2);
		assertThat(Arrays.asList(new Book[] {this.testBookWithid, this.testBook2Withid})).isEqualTo(this.service.getAllBooks());
	}
	
	@Test
	public void findRepeatedBookExistsTest() {
		assertTrue(this.service.findRepeatedBook(testBook));
	}
	
	@Test
	public void findRepeatedBookDoesNotExistTest() {
		assertFalse(this.service.findRepeatedBook(testBook2));
	}
	
	@Test
	public void updateBookTest() {
		Book updatedBook = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		updatedBook.setId(testBookWithid.getId());
		assertThat(updatedBook).isEqualToComparingFieldByField(this.service.updateBook(testBook2, this.testBookWithid.getId()));
	}
	
	@Test(expected = DuplicateValueException.class)
	public void updateBookChangeToExistingNameTest() {
		this.repo.save(this.testBook2);
		this.service.updateBook(this.testBook2, this.testBookWithid.getId());
		fail();
	}
	
	@Test
	public void verifyValidNewBookTest() {
		assertTrue(this.service.verifyValidBook(testBook2Withid, true));
	}
	
	@Test
	public void verifyValidExistingBookTest() {
		assertTrue(this.service.verifyValidBook(testBookWithid, false));
	}
	
	@Test(expected = DuplicateValueException.class)
	public void verifyValidNewBookAlreadyExistsTest() {
		this.service.verifyValidBook(testBookWithid, true);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidNewBookTitleTooLongTest() {
		this.testBook2Withid.setTitle(this.length151);
		this.service.verifyValidBook(testBook2Withid, true);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidExistingBookTitleTooLongTest() {
		this.testBookWithid.setTitle(this.length151);
		this.service.verifyValidBook(testBookWithid, false);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidNewBookSeriesTooLongTest() {
		this.testBook2Withid.setSeries(this.length61);
		this.service.verifyValidBook(testBook2Withid, true);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidExistingBookSeriesTooLongTest() {
		this.testBookWithid.setSeries(this.length61);
		this.service.verifyValidBook(testBookWithid, false);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidNewBookNegativeTimesReadTest() {
		this.testBook2Withid.setTimesRead(-1);
		this.service.verifyValidBook(testBook2Withid, true);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidExistingBookNegativeTimesReadTest() {
		this.testBookWithid.setTimesRead(-1);
		this.service.verifyValidBook(testBookWithid, false);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidNewBookTooManyTimesReadTest() {
		this.testBook2Withid.setTimesRead(1001);
		this.service.verifyValidBook(testBook2Withid, true);
		fail();
	}
	
	@Test(expected = InvalidEntryException.class)
	public void verifyValidExistingBookTooManyTimesReadTest() {
		this.testBookWithid.setTimesRead(1001);
		this.service.verifyValidBook(testBookWithid, false);
		fail();
	}

	@Test
	public void addAuthorToBookTest() {
		Book updatedBook = new Book(this.testBook.getTitle(), this.testBook.getSeries(), this.testBook.getTimesRead(), this.testBook.getOwned(), this.testBook.getCompletion());
		updatedBook.setId(testBookWithid.getId());
		List<Author> authors = Arrays.asList(new Author[] {this.testAuthorWithId});
		updatedBook.getAuthors().addAll(authors);
		assertThat(updatedBook).isEqualToComparingFieldByField(this.service.addAuthorToBook(this.testBookWithid.getId(), authors));
	}
	
	@Test
	public void updateBookAuthorsTest() {
		Book updatedBook = new Book(this.testBook.getTitle(), this.testBook.getSeries(), this.testBook.getTimesRead(), this.testBook.getOwned(), this.testBook.getCompletion());
		updatedBook.setId(testBookWithid.getId());
		List<Author> authors = Arrays.asList(new Author[] {this.testAuthorWithId, this.testAuthor2});
		updatedBook.getAuthors().addAll(Arrays.asList(new Author[] {this.testAuthorWithId, this.testAuthorWithId2}));
		assertThat(updatedBook).isEqualToComparingFieldByField(this.service.updateBookAuthors(this.testBookWithid.getId(), authors));		
	}
	
	@Test
	public void removeAllAuthorsTest() {
		List<Author> authors = Arrays.asList(new Author[] {this.testAuthorWithId, this.testAuthor2});
		Set<Author> emptyAuthors = new HashSet<>();
		this.service.addAuthorToBook(this.testBookWithid.getId(), authors);
		assertThat(emptyAuthors).isEqualTo(this.service.removeAllAuthors(this.testBookWithid.getId()));

	}
}
