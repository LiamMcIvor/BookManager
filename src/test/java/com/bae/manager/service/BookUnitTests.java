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

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
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
	
	private final long id = 1L;
	
	private String length251 = "PebvtPIUTFWcyFFtucstLjqIhztILbbWHnoMZpfMupJsQjdqxDcpFfDtrJcdajvmqqocwlbzjROsLYcgZgWyboQPzxCdhVrvXnXJEXOhkzSGoEyeWFlkvHIkiDJIjsWRqZcVbpwZoRqsgdRVxDjWQvMPuIeYQnqxCDpdTkvaFnCdoPSYKWjPKIyOGbRJCurpbkoBgTmmc"
			+ "XhAcsWAgQPahSNCcaHuvsHNruwYTgtDynDOswCtEuHRCfAxpAh";
	
	private long invalidId;
	
	private Book testBookFail;
	
	private Book testBookFailWithId;
	
	@Before
	public void init() {
		System.out.println("entered");
//		this.bookList = new ArrayList<>();
//		this.testBook = new Book("The Colour of Magic", "9780061685965", "Discworld", 2, Owned.OWNED, Completion.READING);
//		System.out.println("here");
//		this.testBookWithId = new Book(testBook.getTitle(), testBook.getIsbn(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
//		this.testBookWithId.setId(id);
//		this.bookList.add(testBook);
//		this.bookList.add(testBook);
//		this.testBookFail = new Book(testBook.getTitle(), testBook.getIsbn(), testBook.getSeries(), testBook.getTimesRead(), testBook.getOwned(), testBook.getCompletion());
//		this.testBookFail.setId(id);
//		this.testBookFailWithId = new Book(testBookFail.getTitle(), testBookFail.getIsbn(), testBookFail.getSeries(), testBookFail.getTimesRead(), testBookFail.getOwned(), testBookFail.getCompletion());
//		this.testBookFailWithId.setId(id);
//		this.invalidId = 2L;
	}

	@Test
	void createBookTest() {
		System.out.println("here");
		when(this.repo.save(this.testBook)).thenReturn(this.testBookWithId);
		assertEquals(this.testBookWithId, this.service.createBook(this.testBook));
		verify(this.repo, times(1)).save(this.testBook);		
	}
	
	

}
