package com.bae.manager.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(MockitoJUnitRunner.class)
public class BookUnitTests {
	
	@InjectMocks
	private BookService service;

	@Mock
	private BookRepo repo;
	
	@Mock
	private AuthorService authorService;
	
	private List<Book> bookList;
	
	private Book testBook;

	private Book testBookWithId;
	
	private final long id = 1L;
	
	private final String length151 = "GAATACCGATCATGATCCCCAACGTGCATTTACCAGAGGGTCATATTCTAAATAGGGTATTATGAGTCATCTGAATCTGTCCATGCGATTCGGGGCACCGATGTCGCGGACACGGTTTAAAATCCTATCAACCAGGTGACAATATGCCATC";
	
	private final String length61 = "CCACGTTTGTACCTAACCAGCGATTAGTAGTGATCTGGTTATTTGGATAGCGCTTTTTGTT";
	
	private long invalidId;
	
	private Book testBookFail;
	
	private Book testBookFailWithId;

	private Author testAuthor;

	private Author testAuthorWithId;
	
	private Author testAuthor2;
	
	private Author testAuthor2WithId;
	
	private List<Author> authorList;

	@Before
	public void init() {
		this.bookList = new ArrayList<>();
		this.authorList = new ArrayList<>();
		this.testBook = new Book("The Colour of Magic", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBookWithId = new Book(testBook.getTitle(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
		this.testBookWithId.setId(id);
		this.bookList.add(testBook);
		this.bookList.add(testBook);
		this.testBookFail = new Book(testBook.getTitle(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
		this.testBookFail.setId(id);
		this.testBookFailWithId = new Book(testBookFail.getTitle(), testBookFail.getSeries(), testBookFail.getTimesRead(), testBookFail.getOwned(), testBookFail.getCompletion());
		this.testBookFailWithId.setId(id);
		this.invalidId = 2L;
		
		this.testAuthor = new Author("Terry Pratchett");
		this.testAuthorWithId = new Author(testAuthor.getPenName());
		this.testAuthorWithId.setId(this.id);
		this.testAuthor2 = new Author("Neil Gaiman");
		this.testAuthor2WithId = new Author(testAuthor2.getPenName());
		this.testAuthor2WithId.setId(this.invalidId);
		this.authorList.add(testAuthor);
		this.authorList.add(testAuthor2WithId);

	}

	@Test
	public void createBookTest() {
		when(this.repo.save(this.testBook)).thenReturn(this.testBookWithId);
		assertEquals(this.testBookWithId, this.service.createBook(this.testBook));
		verify(this.repo, times(1)).save(this.testBook);		
	}
	
	@Test
	public void duplicateBookTest() {
		testBookFail.setTitle("The Color of Magik");
		when(this.repo.findAll()).thenReturn(this.bookList);
		assertTrue(this.service.findRepeatedBook(this.testBook));
		assertFalse(this.service.findRepeatedBook(this.testBookFail));
		verify(this.repo, times(2)).findAll();
		}
	
	@Test
	public void createDuplicateBookTest() {
		when(this.repo.findAll()).thenReturn(this.bookList);
		assertThrows(DuplicateValueException.class, () -> {
			this.service.createBook(this.testBook);
			verify(this.repo, times(1)).findAll();
		});
	}
	
	@Test
	public void titleTooLongTest() {
		this.testBookFail.setTitle(length151);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createBook(this.testBookFail);
		});
	}
	
	@Test
	public void seriesTooLongTest() {
		this.testBookFail.setSeries(length61);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createBook(this.testBookFail);
		});
	}
	
	@Test
	public void timesReadRulesTest() {
		this.testBookFail.setTimesRead(-1);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createBook(this.testBookFail);
		});
		this.testBookFail.setTimesRead(1001);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createBook(this.testBookFail);
		});
	}
	
	@Test
	public void getAllBooksTest() {
		when(this.repo.findAll()).thenReturn(this.bookList);
		assertEquals(this.bookList, this.service.getAllBooks());
	}
	
	@Test
	public void updateBooksTest() {
		Book newBook = new Book("The color of Magic", "DiskWorld", 3, Owned.WISHLIST, Completion.TO_READ);
		Book updatedBook = new Book(newBook.getTitle(), newBook.getSeries(), newBook.getTimesRead(), newBook.getOwned(), newBook.getCompletion());
		updatedBook.setId(this.id);
		
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testBookWithId));
		when(this.repo.save(updatedBook)).thenReturn(updatedBook);
		
		assertEquals(updatedBook, this.service.updateBook(newBook, this.id));
		
		verify(this.repo, times(1)).findById(this.id);
		verify(this.repo, times(1)).save(updatedBook);
		
	}
	
	@Test
	public void deleteBookTest() {
		when(this.repo.existsById(this.id)).thenReturn(true, false);
		
		assertFalse(this.service.deleteBook(this.id));
		assertThrows(EntryNotFoundException.class, () -> {
			this.service.deleteBook(this.id);
		});
		verify(this.repo, times(1)).deleteById(this.id);
		verify(this.repo, times(3)).existsById(this.id);
		
	}
	
	@Test
	public void findBookByIdTest() {
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testBookWithId));
		
		assertEquals(testBookWithId, this.service.findBookById(this.id));
		assertThrows(EntryNotFoundException.class, () -> {
			this.service.findBookById(this.invalidId);
		});
		verify(this.repo, times(1)).findById(this.id);
	}
	
	@Test
	public void addAuthorToBookTest() {
		this.testBookWithId.getAuthors().add(this.testAuthor);
		
		when(this.repo.saveAndFlush(this.testBookWithId)).thenReturn(this.testBookWithId);
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testBookWithId));
		
		assertEquals(this.testBookWithId, this.service.addAuthorToBook(this.id, this.authorList));
		verify(this.repo, times(1)).findById(this.id);
		verify(this.repo, times(1)).saveAndFlush(this.testBookWithId);

	}
	
	@Test
	public void updateBookAuthors() {
		this.testBookWithId.getAuthors().add(this.testAuthorWithId);
		this.testBookWithId.getAuthors().add(this.testAuthor2WithId);
		
		when(this.repo.saveAndFlush(this.testBookWithId)).thenReturn(this.testBookWithId);
		when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testBookWithId));
		when(this.authorService.findRepeatedAuthor(this.testAuthor)).thenReturn(false);
		when(this.authorService.findRepeatedAuthor(this.testAuthor2)).thenReturn(true);
		when(this.authorService.createAuthor(this.testAuthor)).thenReturn(this.testAuthorWithId);


		assertEquals(this.testBookWithId, this.service.updateBookAuthors(this.id, this.authorList));
		verify(this.repo, times(2)).findById(this.id);
		verify(this.repo, times(1)).saveAndFlush(this.testBookWithId);
		verify(this.authorService, times(1)).findRepeatedAuthor(this.testAuthor);
		verify(this.authorService, times(1)).findRepeatedAuthor(this.testAuthor2);
		verify(this.authorService, times(1)).createAuthor(testAuthor);
	}

}
