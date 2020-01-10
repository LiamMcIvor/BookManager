package com.bae.manager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.BookRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceIntegrationTests {
	
	@Autowired
	private BookRepo repo;

	@Autowired
	private BookService service;
	
	private Book testBook;
	
	private Book testBookWithid;
	
	private Book testBook2;
	
	private Book testBook2Withid;
	
	private final Long id = 2L;
	
	public BookServiceIntegrationTests() {
	}
	
	@Before
	public void init() {
		this.testBook = new Book("The Colour of Magic", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBook2 = new Book("Good Omens", "N/A", 0, Owned.WISHLIST, Completion.TO_READ);
		this.testBook2Withid = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		this.testBook2Withid.setId(this.id);
		this.repo.deleteAll();
		this.testBookWithid = this.repo.save(this.testBook);		
	}
	
	@Test
	public void createBookTest() {
		assertEquals(this.testBook2Withid, this.service.createBook(this.testBook2));
	}
	
	@Test
	public void deleteBookTest() {
		assertThat(this.service.deleteBook(this.testBookWithid.getId())).isFalse();
	}
	
	@Test
	public void findBookByIdTest() {
		assertEquals(this.testBookWithid, this.service.findBookById(this.testBookWithid.getId()));
	}
	
	@Test
	public void getBooksTest() {
		this.testBook2Withid = this.service.createBook(testBook2);
		assertEquals(Arrays.asList((new Book[] {this.testBookWithid, this.testBook2Withid})), this.service.getAllBooks());
	}
	
	@Test
	public void findRepeatedBookTest() {
		assertTrue(this.service.findRepeatedBook(testBook));
	}
	
	@Test
	public void updateBookTest() {
		Book updatedBook = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		updatedBook.setId(testBookWithid.getId());
		
		assertEquals(updatedBook, this.service.updateBook(testBook2, this.testBookWithid.getId()));
	}
	
	@Test
	public void verifyValidBook

}
