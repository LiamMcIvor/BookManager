package com.bae.manager.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.service.BookService;

public class BookControllerUnitTest {
	
	@InjectMocks
	private BookController controller;
	
	@Mock
	private BookService service;
	
	private List<Book> bookList;
	
	private Book testBook;
	
	private Book testBookWithId;
	
	private final long id = 1;
	
	@Before
	public void init() {
		this.bookList = new ArrayList<>();
		this.testBook = new Book("The Colour of Magic", "9780061685965", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBookWithId = new Book(testBook.getTitle(), testBook.getIsbn(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
		this.testBookWithId.setId(id);
		this.bookList.add(testBook);
	}
	
	

	@Test
	public void createBookTest() {
		when(this.service.createBook(testBook)).thenReturn(this.testBookWithId);
		assertEquals(this.testBookWithId, this.service.createBook(testBook));
		verify(this.service, times(1)).createBook(testBook);		
	}

}
