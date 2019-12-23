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

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.service.BookService;

@RunWith(MockitoJUnitRunner.class)
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
		assertEquals(this.testBookWithId, this.controller.createBook(testBook));
		verify(this.service, times(1)).createBook(testBook);		
	}
	
	@Test
	public void getAllBooksTest() {
		when(this.service.getAllBooks()).thenReturn(this.bookList);
		assertFalse("The book list is empty", this.controller.getAllBooks().isEmpty());
		verify(this.service, times(1)).getAllBooks();		
	}
	
	@Test
	public void getBookByIdTest() {
		when(this.service.findBookById(this.id)).thenReturn(testBookWithId);
		assertEquals(this.testBookWithId, this.controller.getBook(this.id));
		verify(this.service, times(1)).findBookById(this.id);
	}
	
	@Test
	public void deleteBookByIdTest() {
		this.controller.deleteBook(this.id);
		verify(this.service, times(1)).deleteBook(this.id);
	}
	
	@Test
	public void updateBookTest() {
		Book newBook = new Book("The Color of Magic", "97800616855", "Diskworld", 2, Owned.OWNED, Completion.READ);
		Book updatedBook = new Book(newBook.getTitle(), newBook.getIsbn(), newBook.getSeries(), newBook.getTimesRead(), newBook.getOwned(), newBook.getCompletion());
		updatedBook.setId(this.id);
		when(this.service.updateBook(newBook, this.id)).thenReturn(updatedBook);
		assertEquals(updatedBook, this.controller.updateBook(newBook, this.id));
		verify(this.service, times(1)).updateBook(newBook, this.id);
	}

}
