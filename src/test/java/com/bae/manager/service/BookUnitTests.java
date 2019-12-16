package com.bae.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
class BookUnitTests {
	
	@InjectMocks
	private BookService service;

	@Mock
	private BookRepo repo;
	
	private List<Book> bookList;
	
	private Book testBook;

	private Book testBookWithId;
	
	private final long id = 1;
	
	private long invalidId;
	
	private Book testBookFail;
	
	private Book testBookFailWithId;
	
	@Before
	public void init() {
		this.bookList = new ArrayList<>();
		this.testBook = new Book("The Colour of Magic", "9780061685965", );
		this.testBookWithId = new Book(testBook.getPenName());
		this.testBookWithId.setId(id);
		this.bookList.add(testBook);
		this.bookList.add(testBook);
		this.testBookFail = new Book(this.length251);
		this.testBookFail.setId(id);
		this.testBookFailWithId = new Book(testBookFail.getPenName());
		this.testBookFailWithId.setId(id);
		this.invalidId = 2L;
	}

	@Test
	void createBookTest() {
		
		
	}

}
