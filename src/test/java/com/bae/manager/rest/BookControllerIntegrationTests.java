package com.bae.manager.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bae.manager.enums.Completion;
import com.bae.manager.enums.Owned;
import com.bae.manager.persistence.domain.Author;
import com.bae.manager.persistence.domain.Book;
import com.bae.manager.persistence.repo.AuthorRepo;
import com.bae.manager.persistence.repo.BookRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private BookRepo repo;
	
	@Autowired
	private AuthorRepo authorRepo;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Book testBook;
	
	private Book testBookWithId;
	
	private Book testBook2;
	
	private Book testBook2WithId;
	
	private long id;	
	
	@Before
	public void init() {
		this.repo.deleteAll();
		
		this.testBook = new Book("The Colour of Magic", "Discworld", 2, Owned.OWNED, Completion.READING);
		this.testBookWithId = this.repo.save(this.testBook);
		this.id = this.testBookWithId.getId();
		
		this.testBook2 = new Book("Good Omens", "N/A", 0, Owned.WISHLIST, Completion.TO_READ);
		this.testBook2WithId = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		this.testBook2WithId.setId(this.id + 1L);
	}
	
	@Test
	public void createBookTest() throws Exception {
		String result = this.mock
				.perform(request(HttpMethod.POST, "/book/createBook").contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(this.testBook2)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		assertEquals(this.mapper.writeValueAsString(this.testBook2WithId), result);
	}
	
	@Test
	public void getAllBooksTest() throws Exception {
		List<Book> bookList = new ArrayList<>();
		bookList.add(this.testBookWithId);
		
		String content = this.mock.perform(request(HttpMethod.GET, "/book/getAll").accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(bookList), content);
	}
	
	@Test
	public void getBookTest() throws Exception {
		String url = "/book/get/" + this.id;
		String content = this.mock.perform(request(HttpMethod.GET, url).accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(testBookWithId), content);
	}
	
	@Test
	public void deleteBookTest() throws Exception {
		String url = "/book/delete/" + this.id;
		this.mock.perform(request(HttpMethod.DELETE, url)).andExpect(status().isOk());
	}
	
	@Test
	public void updateBookTest() throws Exception {	
		Book updatedBook = new Book(this.testBook2.getTitle(), this.testBook2.getSeries(), this.testBook2.getTimesRead(), this.testBook2.getOwned(), this.testBook2.getCompletion());
		updatedBook.setId(this.id);
		String url = "/book/updateBook/" + this.id;
		
		String result = this.mock
						.perform(request(HttpMethod.PUT, url).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(this.testBook2)))
						.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
		assertEquals(this.mapper.writeValueAsString(updatedBook), result);
	}
	
	@Test
	public void updateBookAuthorsTest() throws Exception {
		Author testAuthor = new Author("Terry Pratchett");
		Author testAuthorWithId = this.authorRepo.save(testAuthor);
		Author testAuthor2 = new Author("Neil Gaiman");
		Author testAuthor2WithId = new Author(testAuthor2.getPenName());
		testAuthor2WithId.setId(testAuthorWithId.getId() + 1L);
		List<Author> addedAuthors = Arrays.asList(new Author[] {testAuthorWithId, testAuthor2});
		
		Book updatedBook = new Book(this.testBook.getTitle(), this.testBook.getSeries(), this.testBook.getTimesRead(), this.testBook.getOwned(), this.testBook.getCompletion());
		updatedBook.getAuthors().add(testAuthorWithId);
		updatedBook.getAuthors().add(testAuthor2WithId);
		updatedBook.setId(this.id);		
		
		String url = "/book/updateBookAuthors/" + this.id;
		
		String result = this.mock
				.perform(request(HttpMethod.PATCH, url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(addedAuthors)))
				.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
		
		assertEquals(this.mapper.writeValueAsString(updatedBook), result);
	}

}
