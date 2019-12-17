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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.exception.DuplicateValueException;
import com.bae.manager.exception.InvalidEntryException;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
public class BookUnitTests {
	
	@InjectMocks
	private BookService service;

	@Mock
	private BookRepo repo;
	
	private List<Book> bookList;
	
	private Book testBook;

	private Book testBookWithId;
	
	private final long id = 1L;
	
	private String length251 = "PebvtPIUTFWcyFFtucstLjqIhztILbbWHnoMZpfMupJsQjdqxDcpFfDtrJcdajvmqqocwlbzjROsLYcgZgWyboQPzxCdhVrvXnXJEXOhkzSGoEyeWFlkvHIkiDJIjsWRqZcVbpwZoRqsgdRVxDjWQvMPuIeYQnqxCDpdTkvaFnCdoPSYKWjPKIyOGbRJCurpbkoBgTmmc"
			+ "XhAcsWAgQPahSNCcaHuvsHNruwYTgtDynDOswCtEuHRCfAxpAh";
	
	private long invalidId;
	
	private Book testBookFail;
	
	private Book testBookFailWithId;
	
	@Before
	public void init() {
		this.bookList = new ArrayList<>();
		this.testBook = new Book("The Colour of Magic", "9780061685965", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBookWithId = new Book(testBook.getTitle(), testBook.getIsbn(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
		this.testBookWithId.setId(id);
		this.bookList.add(testBook);
		this.bookList.add(testBook);
		this.testBookFail = new Book(testBook.getTitle(), testBook.getIsbn(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
		this.testBookFail.setId(id);
		this.testBookFailWithId = new Book(testBookFail.getTitle(), testBookFail.getIsbn(), testBookFail.getSeries(), testBookFail.getTimesRead(), testBookFail.getOwned(), testBookFail.getCompletion());
		this.testBookFailWithId.setId(id);
		this.invalidId = 2L;
	}

	@Test
	public void createBookTest() {
		when(this.repo.save(this.testBook)).thenReturn(this.testBookWithId);
		assertEquals(this.testBookWithId, this.service.createBook(this.testBook));
		verify(this.repo, times(1)).save(this.testBook);		
	}
	
	@Test
	public void duplicateBookTest() {
		when(this.repo.findAll()).thenReturn(this.bookList);
		assertTrue(this.service.findRepeatedBook(this.testBook));
		System.out.println(this.testBook);
		System.out.println(this.testBookFail);
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
		testBookFail.setTitle(length251);
		assertThrows(InvalidEntryException.class, () -> {
			this.service.createBook(this.testBookFail);
		});
		testBookFail.setTitle(testBook.getTitle());
	}
	
	@Test
	public void 
	
	

}
